package com.example.application.views.list;

import com.example.application.data.entity.Contact;
import com.example.application.views.articleSource.ArticleSourceView;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleSourceViewTest {

    @Autowired
    private ArticleSourceView articleSourceView;

    @Test
    public void formShownWhenContactSelected() {
        //Grid<Contact> grid = listView.grid;
        //Contact firstContact = getFirstItem(grid);

        //SourceForm form = listView.form;

        //Assert.assertFalse(form.isVisible());
        //grid.asSingleSelect().setValue(firstContact);
        //Assert.assertTrue(form.isVisible());
        //Assert.assertEquals(firstContact.getFirstName(), form.firstName.getValue());
    }
    private Contact getFirstItem(Grid<Contact> grid) {
        return( (ListDataProvider<Contact>) grid.getDataProvider()).getItems().iterator().next();
    }
}