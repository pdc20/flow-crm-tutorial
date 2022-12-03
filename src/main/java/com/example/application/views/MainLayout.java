package com.example.application.views;

import com.example.application.data.entity.Tag;
import com.example.application.security.SecurityService;
import com.example.application.views.articleSource.ArticleSourceView;
import com.example.application.views.question.QuestionView;
import com.example.application.views.tag.TagView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.Router;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {
    private final SecurityService securityService;

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Vaadin CRM");
        logo.addClassNames("text-l", "m-m");

        Button logout = new Button("Log out", e -> securityService.logout());

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);

    }

    private void createDrawer() {
        RouterLink listLink = new RouterLink("Sources", ArticleSourceView.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink tagLink = new RouterLink("Tags", TagView.class);

        RouterLink questionLink = new RouterLink("Questions", QuestionView.class);

        addToDrawer(new VerticalLayout(
            listLink,
            tagLink,
            questionLink
            //new RouterLink("Dashboard", DashboardView.class)
        ));
    }
}
