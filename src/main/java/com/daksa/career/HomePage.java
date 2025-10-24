package com.daksa.career;

import com.vaadin.cdi.annotation.RouteScoped;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
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

        // id field
        var idField = new TextField();
        idField.addClassName("register-text-field");
        idField.setRequired(true);
        idField.setMaxLength(16);

        // name field
        var nameField = new TextField();
        nameField.addClassName("register-text-field");
        nameField.setRequired(true);
        nameField.setMaxLength(64);

        // address field
        var addressField = new TextArea();
        addressField.addClassName("register-text-field");
        addressField.setRequired(false);
        addressField.setMaxLength(255);

        // birth date field
        var birthDateField = new DatePicker();
        birthDateField.addClassName("register-date-field");
        birthDateField.setRequired(true);

        // add radio button for Allow Negative Balance
        RadioButtonGroup<Boolean> allowNegativeBalanceField = new RadioButtonGroup<>();
        allowNegativeBalanceField.addClassName("register-radio-button");
        allowNegativeBalanceField.setItems(true, false);
        allowNegativeBalanceField.setItemLabelGenerator(item -> item ? "Yes" : "No");
        allowNegativeBalanceField.setRequired(true);

        // submit button
        Button submitButton = new Button();
        submitButton.setText("Submit");
        submitButton.addClickListener(this::register);

        // add fields as FormItems (label is provided here)
        registrationForm.addFormItem(idField, "ID");
        registrationForm.addFormItem(nameField, "Name");
        registrationForm.addFormItem(addressField, "Address");
        registrationForm.addFormItem(birthDateField, "Birth Date");
        registrationForm.addFormItem(allowNegativeBalanceField, "Allow Negative Balance");
        registrationForm.addFormItem(submitButton, "");
        registrationForm.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.ASIDE));

        /// add form to container
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
        Label label = new Label("Upload Batch");
        label.addClassName("title");

        Div uploadPanel = new Div(label);
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
        Label label = new Label("Account Table");
        label.addClassName("title");

        Div accountTablePanel = new Div(label);
        accountTablePanel.addClassName("acc-table-container");
        // TODO : code here
        Grid<Account> accountGrid = new Grid<>(Account.class, false);
        accountGrid.addColumn(Account::getId).setHeader("ID").setAutoWidth(true);
        accountGrid.addColumn(Account::getName).setHeader("Name").setAutoWidth(true);
        accountGrid.addColumn(Account::getAddress).setHeader("Address").setAutoWidth(true);
        accountGrid.addColumn(Account::getBirthDate).setHeader("Birth Date").setAutoWidth(true);
        accountGrid.addColumn(account -> account.isAllowNegativeBalance() ? "Yes" : "No")
                .setHeader("Allow Negative Balance").setAutoWidth(true);

        accountTablePanel.add(accountGrid);

        // TODO : set items from repository

        return accountTablePanel;
    }

}
