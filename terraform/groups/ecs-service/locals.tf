# Define all hardcoded local variable and local variables looked up from data resources
locals {
  stack_name                = "identity" # this must match the stack name the service deploys into
  name_prefix               = "${local.stack_name}-${var.environment}"
  global_prefix             = "global-${var.environment}"
  service_name              = "emergency-auth-code-web"
  container_port            = "8080"
  eric_port                 = "10000"
  docker_repo               = "emergency-auth-code-web"
  kms_alias                 = "alias/${var.aws_profile}/environment-services-kms"
  lb_listener_rule_priority = 31
  lb_listener_paths = [
    "/auth-code-requests/accessibility-statement",
    "/auth-code-requests/company/*/cannot-use-this-service",
    "/auth-code-requests/company/*/confirm",
    "/auth-code-requests/requests/*/confirm-officer",
    "/auth-code-requests/requests/*/confirmation",
    "/auth-code-requests/requests/*/officers",
    "/auth-code-requests/start",
  ]
  healthcheck_path          = "/auth-code-requests/healthcheck"
  healthcheck_matcher       = "200"
  vpc_name                  = local.stack_secrets["vpc_name"]
  s3_config_bucket          = data.vault_generic_secret.shared_s3.data["config_bucket_name"]
  app_environment_filename  = "emergency-auth-code-web.env"
  use_set_environment_files = var.use_set_environment_files
  application_subnet_ids    = data.aws_subnets.application.ids

  stack_secrets              = jsondecode(data.vault_generic_secret.stack_secrets.data_json)
  application_subnet_pattern = local.stack_secrets["application_subnet_pattern"]

  service_secrets = merge(jsondecode(data.vault_generic_secret.service_secrets.data_json),
    { aws_kms_key_encrypt = data.aws_kms_key.api_kms_key.arn },
  { aws_kms_key_decrypt = data.aws_kms_key.api_kms_key.arn })

  # create a map of secret name => secret arn to pass into ecs service module
  # using the trimprefix function to remove the prefixed path from the secret name
  secrets_arn_map = {
    for sec in data.aws_ssm_parameter.secret :
    trimprefix(sec.name, "/${local.name_prefix}/") => sec.arn
  }

  global_secrets_arn_map = {
    for sec in data.aws_ssm_parameter.global_secret :
    trimprefix(sec.name, "/${local.global_prefix}/") => sec.arn
  }

  global_secret_list = flatten([for key, value in local.global_secrets_arn_map :
    { "name" = upper(key), "valueFrom" = value }
  ])

  ssm_global_version_map = [
    for sec in data.aws_ssm_parameter.global_secret : {
      name = "GLOBAL_${var.ssm_version_prefix}${replace(upper(basename(sec.name)), "-", "_")}", value = sec.version
    }
  ]

  # get eric secrets from global secrets map
  eric_secrets = [
    { "name" : "API_KEY", "valueFrom" : local.global_secrets_arn_map.eric_api_key },
    { "name" : "AES256_KEY", "valueFrom" : local.global_secrets_arn_map.eric_aes256_key }
  ]
  eric_environment_filename = "eric.env"

  service_secrets_arn_map = {
    for sec in module.secrets.secrets :
    trimprefix(sec.name, "/${local.service_name}-${var.environment}/") => sec.arn
  }

  service_secret_list = flatten([for key, value in local.service_secrets_arn_map :
    { "name" = upper(key), "valueFrom" = value }
  ])

  ssm_service_version_map = [
    for sec in module.secrets.secrets : {
      name = "${replace(upper(local.service_name), "-", "_")}_${var.ssm_version_prefix}${replace(upper(basename(sec.name)), "-", "_")}", value = sec.version
    }
  ]

  # secrets to go in list
  task_secrets = concat(local.service_secret_list, local.global_secret_list, [])

  task_environment = concat(local.ssm_global_version_map, local.ssm_service_version_map, [
    { "name" : "PORT", "value" : local.container_port },
    { "name" : "LOGLEVEL", "value" : var.log_level }
  ])
}
