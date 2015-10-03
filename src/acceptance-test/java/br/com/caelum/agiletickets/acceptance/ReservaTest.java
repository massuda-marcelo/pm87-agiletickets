package br.com.caelum.agiletickets.acceptance;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import br.com.caelum.agiletickets.PreencheBanco;

public class ReservaTest {

		private WebDriver browser;
		
		@Before
		public void setUp() throws Exception {
			browser = new FirefoxDriver();
		}
		
		@After
		public void tearDown() {
			browser.close();
		}
		
		@Test
		public void testaIngressoMaisCaroSessaoCheia() {
			PreencheBanco.main(null);
			
			browser.get("http://localhost:8080/");
			
			browser.findElement(By.partialLinkText("100")).click();
			browser.findElement(By.id("qtde")).sendKeys("95");
			WebElement div = browser.findElement(By.id("content"));
			WebElement form = div.findElement(By.tagName("form"));
//			form.findElements(By.tagName("input")).get(1).click();
			form.submit();
			browser.findElement(By.partialLinkText(": 5")).click();
			browser.findElement(By.id("qtde")).sendKeys("1");
			div = browser.findElement(By.id("content"));
			form = div.findElement(By.tagName("form"));
			form.findElements(By.tagName("input")).get(1).click();
			
			assertTrue(browser.findElement(By.id("message")).getText().contains("R$ 55,00"));
			
			
		}
}
