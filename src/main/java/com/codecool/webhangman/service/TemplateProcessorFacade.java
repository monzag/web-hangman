package com.codecool.webhangman.service;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class TemplateProcessorFacade {
    private JtwigTemplate template;
    private JtwigModel model;

    public TemplateProcessorFacade(String templatePath) {
        this.template = JtwigTemplate.classpathTemplate(templatePath);
        this.model = JtwigModel.newModel();
    }

    public <T> void modelWith(String name, T object) {
        this.model.with(name, object);
    }

    public String render() {
        return this.template.render(this.model);
    }
}
