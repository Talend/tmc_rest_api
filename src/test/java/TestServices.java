import com.talend.tmc.dom.Workspace;
import com.talend.tmc.services.*;
import com.talend.tmc.services.workspaces.WorkspaceService;
import org.apache.cxf.jaxrs.ext.search.client.SearchConditionBuilder;
import org.junit.Test;

import java.io.IOException;

public class TestServices {

    private final String bearerToken = "zVZUDu4FSRyS4GI8qSvYTpG7oKUGC3nN98GXyS8laERUFbKgtuHtnHNN1jR7SUw4";
    @Test
    public void testWorkspaces() {
        TalendCredentials credentials = new TalendBearerAuth(bearerToken);
        WorkspaceService service = WorkspaceService.instance(credentials,TalendCloudRegion.AWS_USA_EAST);
        try {
            SearchConditionBuilder fiql = SearchConditionBuilder.instance("fiql");


            String params = fiql.is("owner").equalTo("tbennett").query();

            Workspace[] workspaces = service.get(params);
            for (Workspace workspace : workspaces)
                System.out.println(workspace.toString());
        } catch (TalendRestException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
