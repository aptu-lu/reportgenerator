package Readers;

import Entity.ColumnSettings;
import Entity.PageSettings;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SettingsReader {
    private List<ColumnSettings> listColumnSettings = new ArrayList<>();
    private PageSettings pageSettings = new PageSettings();

    public void parse(String filePath) {
        File file = new File(filePath);
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
            document.getDocumentElement().normalize();
            Node page = document.getElementsByTagName("page").item(0);
            if (page.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) page;
                int width = Integer.parseInt(element.getElementsByTagName("width").item(0).getTextContent());
                pageSettings.setWidth(width);
                int height = Integer.parseInt(element.getElementsByTagName("height").item(0).getTextContent());
                pageSettings.setHeight(height);
            }
            NodeList columns = document.getElementsByTagName("column");
            for (int i = 0; i < columns.getLength(); i++) {
                ColumnSettings columnSettings = new ColumnSettings();
                Node node = columns.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String title = element.getElementsByTagName("title").item(0).getTextContent();
                    columnSettings.setTitle(title);
                    int width = Integer.parseInt(element.getElementsByTagName("width").item(0).getTextContent());
                    columnSettings.setWidth(width);
                    listColumnSettings.add(columnSettings);
                }
            }
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public List<ColumnSettings> getListColumnSettings() {
        return listColumnSettings;
    }

    public PageSettings getPageSettings() {
        return pageSettings;
    }
}