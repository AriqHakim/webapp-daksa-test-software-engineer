package com.daksa.career;

import com.vaadin.cdi.annotation.RouteScoped;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.StringLengthValidator;
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

                // id field
                var idField = new TextField();
                idField.addClassName("register-text-field");
                idField.setRequired(true);
                idField.setMaxLength(16);

                // bind id field to binder
                accountBinder.forField(idField)
                                .asRequired("Please enter your ID")
                                .withValidator(new StringLengthValidator(
                                                "Title must be between 1 and 16 characters", 1, 16))
                                .withValidator(accId -> !accountService.isIdExisting(accId),
                                                "ID already exists")
                                .bind(Account::getId, Account::setId);

                // name field
                var nameField = new TextField();
                nameField.addClassName("register-text-field");
                nameField.setRequired(true);
                nameField.setMaxLength(64);

                // bind id field to name
                accountBinder.forField(nameField)
                                .asRequired("Please enter your name")
                                .withValidator(new StringLengthValidator(
                                                "Title must be between 1 and 64 characters", 1, 64))
                                .bind(Account::getName, Account::setName);

                // address field
                var addressField = new TextArea();
                addressField.addClassName("register-text-field");
                addressField.setRequired(false);
                addressField.setMaxLength(255);

                // bind address field to address
                accountBinder.forField(addressField)
                                .withValidator(new StringLengthValidator(
                                                "Address must be between 0 and 255 characters", 0, 255))
                                .bind(Account::getAddress, Account::setAddress);

                // birth date field
                var birthDateField = new DatePicker();
                birthDateField.addClassName("register-date-field");
                birthDateField.setRequired(true);

                // bind birth date field to birth date
                accountBinder.forField(birthDateField)
                                .asRequired("Please enter your date of birth")
                                .bind(Account::getBirthDate, Account::setBirthDate);

                // add radio button for Allow Negative Balance
                RadioButtonGroup<Boolean> allowNegativeBalanceField = new RadioButtonGroup<>();
                allowNegativeBalanceField.addClassName("register-radio-button");
                allowNegativeBalanceField.setItems(true, false);
                allowNegativeBalanceField.setItemLabelGenerator(item -> item ? "Yes" : "No");
                allowNegativeBalanceField.setRequired(true);

                // bind radio button for Allow Negative Balance
                accountBinder.forField(allowNegativeBalanceField)
                                .asRequired("Please choose allow negative balance option")
                                .bind(Account::isAllowNegativeBalance, Account::setAllowNegativeBalance);

                // submit button
                Button submitButton = new Button();
                submitButton.setText("Submit");
                submitButton.addClickListener(this::register);

                // add fields as FormItems
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
                if (accountBinder.validate().isOk()) {
                        Account account = new Account();
                        accountBinder.writeBeanIfValid(account);
                        accountService.register(account);
                        accountGrid.refreshAll();
                        accountBinder.readBean(new Account());

                        Notification notification = Notification.show("Register account success!", 5000,
                                        Notification.Position.TOP_END);
                        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                }
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
                // uploadBatchButton.setDropAllowed(false);
                uploadBatchButton.setAutoUpload(false);
                uploadBatchButton.setAcceptedFileTypes(".txt");

                // rejectedf listener
                uploadBatchButton.addFileRejectedListener(event -> {
                        String errorMessage = event.getErrorMessage();

                        Notification notification = Notification.show(errorMessage, 5000,
                                        Notification.Position.TOP_END);
                        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                });

                // successed listener
                uploadBatchButton.addSucceededListener(event -> {
                        try {
                                LOG.info("uploadRegBatch");
                                accountService.parseBatch(uploadBatchMemoryBuffer.getInputStream());
                                accountGrid.refreshAll();

                                Notification notification = Notification.show("Upload in batch success!", 5000,
                                                Notification.Position.TOP_END);
                                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
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
                accountGrid = new AccountGrid(() -> accountRepository.getAccounts());

                accountTablePanel.add(accountGrid);

                return accountTablePanel;
        }

}
