package com.smu.views.personalinformation;

import com.smu.data.entity.Person;
import com.smu.data.entity.PersonDto;
import com.smu.data.entity.User;
import com.smu.security.AuthenticatedUser;
import com.smu.service.PersonService;
import com.smu.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.security.PermitAll;
import java.time.LocalDate;
import java.util.Optional;

@PageTitle("Personal Information")
@Route(value = "personal", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
@Slf4j
public class PersonalInformationView extends Div {

    private final TextField firstName = new TextField("First name");
    private final TextField lastName = new TextField("Last name");
    private final EmailField email = new EmailField("Email address");
    private final DatePicker dateOfBirth = new DatePicker("Birthday");
    private final PhoneNumberField phone = new PhoneNumberField("Phone number");
    private final PasswordField hashedPassword = new PasswordField("Password");
    private final TextField accessKey = new TextField("Access Key");

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private final BeanValidationBinder<PersonDto> binder = new BeanValidationBinder<>(PersonDto.class);

    PersonDto person;
    private final AuthenticatedUser authenticatedUser;
    private final PersonService personService;

    public PersonalInformationView(AuthenticatedUser authenticatedUser, PersonService personService) {
        this.authenticatedUser = authenticatedUser;
        this.personService = personService;
        addClassName("personal-information-view");

        Optional<User> userOp = this.authenticatedUser.get();
        if (userOp.isEmpty()) {
            log.error("Current user doesn't exit!");
            return;
        }
        User user = userOp.get();
        add(createTitle());
        add(createFormLayout(user));
        add(createButtonLayout());

        binder.bindInstanceFields(this);
//        clearForm();

        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> {
            person.setFirstName((String) binder.forField(firstName).getField().getValue());
            person.setLastName((String) binder.forField(lastName).getField().getValue());
            person.setEmail((String) binder.forField(email).getField().getValue());
            person.setPhone((String) binder.forField(phone).getField().getValue());
            person.setAccessKey((String) binder.forField(accessKey).getField().getValue());
            person.setDateOfBirth((LocalDate) binder.forField(dateOfBirth).getField().getValue());
            String password = (String) binder.forField(hashedPassword).getField().getValue();
            if(StringUtils.isNotEmpty(password)){
                person.setHashedPassword(password);
            }
            personService.update(person);
            Notification.show("Person details stored.");
//            clearForm();
        });
    }

    private void clearForm() {
        binder.setBean(new PersonDto());
    }

    private Component createTitle() {
        return new H3("Personal information");
    }

    private Component createFormLayout(User user) {
        person = personService.getByUserId(user.getId());
        firstName.setValue(person.getFirstName());
        lastName.setValue(person.getLastName());
        dateOfBirth.setValue(person.getDateOfBirth());
        phone.setValue(person.getPhone());
        email.setValue(person.getEmail());
        accessKey.setValue(person.getAccessKey());
        firstName.setRequired(true);
        firstName.setErrorMessage("Please enter your first name");
        lastName.setRequired(true);
        lastName.setErrorMessage("Please enter your last name");
        email.setRequiredIndicatorVisible(true);
        email.setErrorMessage("Please enter a valid email address");
        hashedPassword.setRequired(true);
        hashedPassword.setErrorMessage("Please enter a valid password");
        FormLayout formLayout = new FormLayout();
        formLayout.add(firstName, lastName, dateOfBirth, phone, email, accessKey, hashedPassword);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        buttonLayout.add(cancel);
        return buttonLayout;
    }

    private static class PhoneNumberField extends CustomField<String> {
        private final ComboBox<String> countryCode = new ComboBox<>();
        private final TextField number = new TextField();

        public PhoneNumberField(String label) {
            setLabel(label);
            countryCode.setWidth("120px");
            countryCode.setPlaceholder("Country");
            countryCode.setAllowedCharPattern("[\\+\\d]");
            countryCode.setItems("+354", "+91", "+62", "+98", "+964", "+353", "+44", "+972", "+39", "+225");
            countryCode.addCustomValueSetListener(e -> countryCode.setValue(e.getDetail()));
            number.setAllowedCharPattern("\\d");
            HorizontalLayout layout = new HorizontalLayout(countryCode, number);
            layout.setFlexGrow(1.0, number);
            add(layout);
        }

        @Override
        protected String generateModelValue() {
            if (countryCode.getValue() != null && number.getValue() != null) {
                return countryCode.getValue() + " " + number.getValue();
            }
            return "";
        }

        @Override
        protected void setPresentationValue(String phoneNumber) {
            String[] parts = phoneNumber != null ? phoneNumber.split(" ", 2) : new String[0];
            if (parts.length == 1) {
                countryCode.clear();
                number.setValue(parts[0]);
            } else if (parts.length == 2) {
                countryCode.setValue(parts[0]);
                number.setValue(parts[1]);
            } else {
                countryCode.clear();
                number.clear();
            }
        }
    }

}
