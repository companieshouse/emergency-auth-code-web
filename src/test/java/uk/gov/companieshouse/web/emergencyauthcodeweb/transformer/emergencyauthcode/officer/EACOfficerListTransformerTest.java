package uk.gov.companieshouse.web.emergencyauthcodeweb.transformer.emergencyauthcode.officer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.emergencyauthcode.officer.PrivateEACOfficerApi;
import uk.gov.companieshouse.api.model.emergencyauthcode.officer.PrivateEACOfficersListApi;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.officer.EACOfficer;
import uk.gov.companieshouse.web.emergencyauthcodeweb.model.emergencyauthcode.officer.EACOfficerList;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class EACOfficerListTransformerTest {
    private static final String FULL_NAME = "FIRSTNAME MIDDLENAME LASTNAME";
    private static final String TRANSFORMED_NAME = "LASTNAME, Firstname Middlename";
    private static final int ITEMS_PER_PAGE = 1;
    private static final int START_INDEX = 1;
    private static final int TOTAL_RESULTS = 1;
    private List<EACOfficer> items;
    private PrivateEACOfficerApi[] apiItems;

    private EACOfficer eacOfficer;
    private PrivateEACOfficerApi eacOfficerApi;

    private EACOfficerList eacOfficerList;
    private PrivateEACOfficersListApi privateEACOfficersListApi;

    private EACOfficerTransformer eacOfficerTransformer;

    private EACOfficerListTransformer eacOfficerListTransformer;

    @BeforeEach
    void setUp() {
        eacOfficerTransformer = Mappers.getMapper(EACOfficerTransformer.class);
        eacOfficerListTransformer = new EACOfficerListTransformerImpl(eacOfficerTransformer);

        eacOfficerList = new EACOfficerList();
        privateEACOfficersListApi = new PrivateEACOfficersListApi();

        eacOfficer = new EACOfficer();
        eacOfficerApi = new PrivateEACOfficerApi();

        apiItems = new PrivateEACOfficerApi[]{eacOfficerApi};

        items = new ArrayList<>();
        items.add(eacOfficer);

        privateEACOfficersListApi.setItems(apiItems);
        privateEACOfficersListApi.setItemsPerPage(ITEMS_PER_PAGE);
        privateEACOfficersListApi.setStartIndex(START_INDEX);
        privateEACOfficersListApi.setTotalResults(TOTAL_RESULTS);

        eacOfficerList.setItems(items);
        eacOfficerList.setItemsPerPage(ITEMS_PER_PAGE);
        eacOfficerList.setStartIndex(START_INDEX);
        eacOfficerList.setTotalResults(TOTAL_RESULTS);
    }

    @Test
    @DisplayName("Test mapping of PrivateEACOfficersListApi to EACOfficerList")
    void validateApiToClient() {
        eacOfficerApi.setName(FULL_NAME);
        EACOfficerList result = eacOfficerListTransformer.apiToClient(privateEACOfficersListApi);

        assertEquals(eacOfficerList.getItemsPerPage(), result.getItemsPerPage());
        assertEquals(eacOfficerList.getStartIndex(), result.getStartIndex());
        assertEquals(eacOfficerList.getTotalResults(), result.getTotalResults());
        assertEquals(TRANSFORMED_NAME, result.getItems().get(0).getName());
    }
}
