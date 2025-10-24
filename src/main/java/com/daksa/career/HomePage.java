package com.daksa.career;

import com.vaadin.cdi.annotation.RouteScoped;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author Muhammad Rizki
 */
@RouteScoped
@Route(value = "")
@StyleSheet("./frontend/styles/main.css")
public class HomePage extends Div {

    private static final Logger LOG = LoggerFactory.getLogger(HomePage.class);
    private Binder<Account> accountBinder; // save form value
    private AccountGrid accountGrid; // account table
    @Inject
    private AccountService accountService;
    @Inject
    private AccountRepository accountRepository;

    /**
     * This method is invoked after the constructor
     * Defining all component which used in the apps
     */
    @PostConstruct
    public void init() {
        LOG.info("init");
        accountBinder = new Binder<>();
        addClassName("container");
        add(createRegisterPanel(), createUploadPanel(), createAccountTable());
    }

    /**
     * This panel is used for create the register panel on the top of the page
     *
     * @return FormLayout
     *         Fill the rest with defined fields for completing the assignment
     */
    private Div createRegisterPanel() {
        Div regFormContainer = new Div();
        regFormContainer.addClassName("reg-panel-container");

        Label label = new Label("MOCKVA Registration");
        label.addClassName("title");
        FormLayout registrationForm = new FormLayout(label);
        // TODO: Code here
        Button submitButton = new Button();
        submitButton.setText("Submit");
        submitButton.addClickListener(this::register);
        registrationForm.addFormItem(submitButton, "");
        registrationForm.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        regFormContainer.add(registrationForm);
        return regFormContainer;
    }

    /**
     * This method is invoked when submit button of registration panel is clicked
     */
    private void register(ClickEvent<Button> buttonClickEvent) {
        LOG.info("registerButtonClicked");
        // TODO: Code here
    }

    /**
     * Registration Batch Panel
     * This panel shows a button to upload file for registration batch
     * Uploaded file were exists in the project folder /batch-file
     */
    private Div createUploadPanel() {
        Div uploadPanel = new Div();
        uploadPanel.addClassName("upload-panel-container");
        MemoryBuffer uploadBatchMemoryBuffer = new MemoryBuffer();
        Upload uploadBatchButton = new Upload(uploadBatchMemoryBuffer);
        uploadBatchButton.addSucceededListener(event -> {
            try {
                LOG.info("uploadRegBatch");
                // TODO: code here
                accountService.parseBatch(uploadBatchMemoryBuffer.getInputStream());
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        });
        uploadPanel.add(uploadBatchButton);
        return uploadPanel;
    }

    /**
     * This component create Account Table
     */
    private Div createAccountTable() {
        Div accountTablePanel = new Div();
        accountTablePanel.addClassName("acc-table-container");
        // TODO : code here

        return accountTablePanel;
    }

}
