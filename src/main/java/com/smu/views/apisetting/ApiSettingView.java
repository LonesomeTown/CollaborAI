package com.smu.views.apisetting;

import com.smu.data.constant.ChatTabs;
import com.smu.data.entity.ApiSetting;
import com.smu.data.entity.User;
import com.smu.data.enums.ApiTypes;
import com.smu.security.AuthenticatedUser;
import com.smu.service.ApiSettingService;
import com.smu.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@PageTitle("API Setting")
@Route(value = "api-setting", layout = MainLayout.class)
@Uses(Icon.class)
@Slf4j
@RolesAllowed("ADMIN")
public class ApiSettingView extends Div {

    private final ComboBox<String> apiName = new ComboBox<>("API Name");
    private final ComboBox<String> model = new ComboBox<>("Model");
    private final NumberField temperature = new NumberField("Temperature");
    private final IntegerField maxToken = new IntegerField("Max Tokens");
    private final ComboBox<String> imageSize = new ComboBox<>("Image Size");
    private final ComboBox<String> responseFormat = new ComboBox<>("Response Format");

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private final BeanValidationBinder<ApiSetting> binder = new BeanValidationBinder<>(ApiSetting.class);

    ApiSetting apiSetting = new ApiSetting();
    private final ApiSettingService apiSettingService;

    public ApiSettingView(ApiSettingService apiSettingService) {
        this.apiSettingService = apiSettingService;
        addClassName("personal-information-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        binder.bindInstanceFields(this);
        clearForm();

        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> {
            apiSetting.setApiName((String) binder.forField(apiName).getField().getValue());
            apiSetting.setModel((String) binder.forField(model).getField().getValue());
            apiSetting.setImageSize((String) binder.forField(imageSize).getField().getValue());
            apiSetting.setMaxToken((Integer) binder.forField(maxToken).getField().getValue());
            apiSetting.setTemperature((Double) binder.forField(temperature).getField().getValue());
            apiSetting.setResponseFormat((String) binder.forField(responseFormat).getField().getValue());
            this.apiSettingService.save(apiSetting);
            Notification.show("API settings stored.");
        });
    }

    private void clearForm() {
        binder.setBean(new ApiSetting());
    }

    private Component createTitle() {
        return new H3("API setting");
    }

    private Component createFormLayout() {
        apiName.setItems(Arrays.stream(ApiTypes.values()).map(Enum::name).collect(Collectors.toList()));
        apiName.setRequired(true);
        List<String> models = new ArrayList<>();
        apiName.addValueChangeListener(e -> {
            String name = e.getValue();
            if (ApiTypes.CHAT_GPT.name().equals(name)) {
                models.addAll(Arrays.asList("gpt-4", "gpt-4-0314", "gpt-4-32k", "gpt-4-32k-0314", "gpt-3.5-turbo", "gpt-3.5-turbo-0301"));
                model.setValue("gpt-3.5-turbo");
                temperature.setValue(0.5);
                imageSize.clear();
                responseFormat.clear();
            } else if (ApiTypes.IMAGE_GENERATION.name().equals(name)) {
                imageSize.setItems(Arrays.asList("256x256", "512x512", "1024x1024"));
                responseFormat.setItems(Arrays.asList("url", "b64_json"));
                imageSize.setValue("1024x1024");
                responseFormat.setValue("url");
                model.clear();
                temperature.clear();
                maxToken.clear();
            }
        });
        model.setItems(models);
        model.setHelperText("ID of the model to use");
        temperature.setHelperText("What sampling temperature to use, between 0 and 2. Higher values like 0.8 will make the output more random, while lower values like 0.2 will make it more focused and deterministic");
        maxToken.setHelperText("The maximum number of tokens to generate in the chat completion.");
        imageSize.setHelperText("The size of the generated images.");
        responseFormat.setHelperText("The format in which the generated images are returned.");
        FormLayout formLayout = new FormLayout();
        formLayout.add(apiName, model, temperature, maxToken, imageSize, responseFormat);
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

}
