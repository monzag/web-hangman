package com.codecool.webhangman.view;

import com.codecool.webhangman.model.TemplateProcessorFacade;
import org.springframework.stereotype.Service;

@Service
public class StartPageView {

    public String loadStartPageContent() {
        TemplateProcessorFacade processor = new TemplateProcessorFacade("/templates/startScreen.twig");

        String contentCss = "classpath:/" + "templates/cssSettings/login-css-snippet.html";
        processor.modelWith("content_css", contentCss);

        String contentPath = "classpath:/" + "templates/backgroundsnippets/start-screen-snippet.html";
        processor.modelWith("content_path", contentPath);

        return processor.render();
    }
}
