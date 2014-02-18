package com.muzima.view.forms;

import com.muzima.api.model.FormData;
import com.muzima.controller.FormController;
import com.muzima.service.HTMLFormObservationCreator;
import com.muzima.testSupport.CustomTestRunner;
import com.muzima.utils.Constants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;

@RunWith(CustomTestRunner.class)
public class HTMLFormDataStoreTest {

    private FormController formController;
    private HTMLFormWebViewActivity formWebViewActivity;
    private FormData formData;
    private HTMLFormObservationCreator htmlFormObservationCreator;
    private HTMLFormDataStore htmlFormDataStore;

    @Before
    public void setUp() throws Exception {
        formController = mock(FormController.class);
        formWebViewActivity = mock(HTMLFormWebViewActivity.class);
        formData = mock(FormData.class);
        htmlFormObservationCreator = mock(HTMLFormObservationCreator.class);
        htmlFormDataStore = new HTMLFormDataStore(formWebViewActivity, formController, formData){
            @Override
            public HTMLFormObservationCreator getFormParser(){
                return htmlFormObservationCreator;
            }
        };
    }

    @Test
    public void shouldParsedPayloadForCompletedForm() {
        String jsonPayLoad = "jsonPayLoad";
        htmlFormDataStore.saveHTML(jsonPayLoad, Constants.STATUS_COMPLETE);
        verify(htmlFormObservationCreator).createAndPersistObservations(jsonPayLoad);
    }

    @Test
    public void shouldNotParseIncompletedForm() {
        String jsonPayLoad = "jsonPayLoad";
        htmlFormDataStore.saveHTML(jsonPayLoad, Constants.STATUS_INCOMPLETE);
        verify(htmlFormObservationCreator, times(0)).createAndPersistObservations(jsonPayLoad);
    }
}
