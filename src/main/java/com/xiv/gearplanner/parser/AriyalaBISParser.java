package com.xiv.gearplanner.parser;

import com.xiv.gearplanner.exceptions.AriyalaParserException;
import com.xiv.gearplanner.exceptions.UnexpectedHtmlStructureException;
import com.xiv.gearplanner.models.importers.AriyalaBIS;
import com.xiv.gearplanner.models.importers.AriyalaItem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.print.Doc;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AriyalaBISParser {
    private static Logger logger = LoggerFactory.getLogger(AriyalaBISParser.class);
    private boolean verbose = true;
    private final String rootUrl;



    private WebDriver driver = new ChromeDriver();
    private WebDriverWait wait = new WebDriverWait(driver,7);

    public AriyalaBISParser(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    public AriyalaBIS getBISbyId(String id) throws UnexpectedHtmlStructureException {

        if (verbose) {
            logger.info("Parsing Ariala BIS for {}", id);
        }

        AriyalaBIS bis = new AriyalaBIS();
        bis.setId(id);
        String url = rootUrl + id;

        Document html;


        driver.get(url);

        // Waits for tables to be viewable

        //
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;

        // bad id
        if(driver.findElements(By.id("group-food")).isEmpty()) {
            driver.quit();
            return new AriyalaBIS();
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("group-food")));

        html = Jsoup.parse(driver.getPageSource());

        // Get job data
        parseJob(bis, html);

        // Get gear and materia
        parseGear(bis, html);

        bis.setItems(parseMateriaList(bis.getItems(), driver));

        // close instance
        driver.quit();

        if (verbose) {
            logger.info("BIS Object: {}", bis.toString());
        }

        return bis;

    }

    // Gets the job associated with the BIS
    private void parseJob(AriyalaBIS bis, Document html) throws UnexpectedHtmlStructureException {

        Elements classNameBox = html.select("div#categoryBoxContentName");
        ParserUtils.checkElementsSize(classNameBox, 1, "Cannot find html for character name");

        // Matches file name patterns
        String pattern = "([-a-zA-Z0-9]+)(\\.[0-9a-z]{2,5})";

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(classNameBox.attr("style"));

        if (m.find()) {
            bis.setClassAbbr(m.group(1));
        } else {
            System.out.println("NO MATCH");
        }

    }

    // Grabs gear names ilvl and original id
    private void parseGear(AriyalaBIS bis, Document html) {

        Elements selectedItems = html.select("td.itemName.selected > div.cellContent");

        List<AriyalaItem> gearSetBIS = new ArrayList<>();

        for (Element column : selectedItems) {
            AriyalaItem gear = parseItem(column);
            gearSetBIS.add(gear);
        }
        // add completed gear set to BIS list.
        bis.setItems(gearSetBIS);
       // ParserUtils.checkElementsSize(itemColumns, 2, "Cannot find HTML for character gear");
    }

    private AriyalaItem parseItem(Element item) {

        Element ilvlEle = item.selectFirst("span");
        String iLvlStr = ilvlEle.text().replaceAll("[^0-9]","");
        Integer iLvl = Integer.parseInt(iLvlStr);

        System.out.println(iLvl);

        Element link = item.selectFirst("a.floatLeft");
        String [] linkSplit = link.attr("href").split("/", 6);

        System.out.println(linkSplit[4]);

        Long id = Long.parseLong(linkSplit[4]);

        System.out.println(link.attr("href"));
        System.out.println(link.text());

        return new AriyalaItem(id,link.text(),iLvl);
    }

    private List<AriyalaItem> parseMateriaList(List<AriyalaItem> items, WebDriver driver) {

        List<AriyalaItem> ariyalaItems = new ArrayList<>();

        for(AriyalaItem item : items) {
            ariyalaItems.add(parseMateria(item, driver));
        }

        return ariyalaItems;

    }

    // Need to be able to click things and such....
    private AriyalaItem parseMateria(AriyalaItem item, WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor)driver;

        //hide footer.....bs
        hideElement("//div[@id='header']");
        hideElement("//div[@id='footerBar']");

        // shields wont have materia
        if(!item.getName().contains("Shield")) {

            WebElement clickableElement = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//td[contains(., '" + item.getName() + "')]/following-sibling::td[1]")));

            Actions actions = new Actions(driver);
            actions.moveToElement(clickableElement);
            actions.perform();

            js.executeScript("arguments[0].scrollIntoView(true);", clickableElement);
            clickableElement.click();

            try {
                boolean MateriaBox = wait.until(ExpectedConditions.textToBePresentInElementLocated(
                        By.xpath("//span[@id='materiaItem']"), item.getName()));


                for (int i = 0; i < 5; i++) {
                    String iString = String.valueOf(i + 1);
                    Select materiaSelect = new Select(driver.findElement(By.id("materiaSelect" + iString)));
                    WebElement option = materiaSelect.getFirstSelectedOption();
                    item.addMateria(option.getText());

                }

                WebElement closeElement = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(., 'Close Materia Melding')]")));
                js.executeScript("arguments[0].click()", closeElement);

            } catch(org.openqa.selenium.TimeoutException e) {
                if (verbose) {
                    logger.info("Unable to find materia for: {}", item.getName());
                }
            }
        }

        return item;

    }

    public void hideElement(String xpath)
    {
        WebElement element = driver.findElement(By.xpath(xpath));
        ((JavascriptExecutor)driver).executeScript("arguments[0].style.visibility='hidden'", element);
    }


}
