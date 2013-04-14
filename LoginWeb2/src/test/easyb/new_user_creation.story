import ohtu.*
import ohtu.data_access.*
import ohtu.domain.*
import ohtu.authentication.*
import org.openqa.selenium.*
import org.openqa.selenium.htmlunit.HtmlUnitDriver;


description """A new user account can be created 
              if a proper unused username 
              and a proper password are given"""

scenario "creation succesfull with correct username and password", {
    given 'command new user is selected', {
        driver = new HtmlUnitDriver();
        driver.get("http://localhost:8080");
        element = driver.findElement(By.linkText("register new user"));       
        element.click();       
        
    }
 
    when 'a valid username and password are entered', {
        element = driver.findElement(By.name("username"));
        element.sendKeys("esa44");
        element = driver.findElement(By.name("password"));
        element.sendKeys("12345678");
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys("12345678");
        element = driver.findElement(By.name("add"));
        element.submit();
    }

    then 'new user is registered to system', {
        driver.getPageSource().contains("Welcome to Ohtu Application!").shouldBe true
    }
}

scenario "can login with succesfully generated account", {
    given 'command new user is selected', {
        driver = new HtmlUnitDriver();
        driver.get("http://localhost:8080");
        element = driver.findElement(By.linkText("register new user"));       
        element.click();       
    }
 
    when 'a valid username and password are entered', {
        element = driver.findElement(By.name("username"));
        element.sendKeys("esa44");
        element = driver.findElement(By.name("password"));
        element.sendKeys("12345678");
        element = driver.findElement(By.name("login"));
        element.submit();
    }

    then  'new credentials allow logging in to system', {
        driver.getPageSource().contains("Welcome to Ohtu Application!").shouldBe true
    }
}

scenario "creation fails with correct username and too short password", {
    given 'command new user is selected', {
        driver = new HtmlUnitDriver();
        driver.get("http://localhost:8080");
        element = driver.findElement(By.linkText("register new user"));       
        element.click();       
    }
    when 'a valid username and too short password are entered', {
        element = driver.findElement(By.name("username"));
        element.sendKeys("vesa1");
        element = driver.findElement(By.name("password"));
        element.sendKeys("1");
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys("1");
        element = driver.findElement(By.name("add"));
        element.submit();
    }
    then 'new user is not be registered to system', {
        driver.getPageSource().contains("length greater or equal to 8").shouldBe true
    }
}

scenario "creation fails with correct username and pasword consisting of letters", {
    given 'command new user is selected', {
        driver = new HtmlUnitDriver();
        driver.get("http://localhost:8080");
        element = driver.findElement(By.linkText("register new user"));       
        element.click();       
    }
    when 'a valid username and password consisting of letters are entered', {
        element = driver.findElement(By.name("username"));
        element.sendKeys("vesa2");
        element = driver.findElement(By.name("password"));
        element.sendKeys("aaaaaaaa");
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys("aaaaaaaa");
        element = driver.findElement(By.name("add"));
        element.submit();
    }
    then 'new user is not be registered to system', {
        driver.getPageSource().contains("Create username and give password").shouldBe true
    }
}

scenario "creation fails with too short username and valid pasword", {
    given 'command new user is selected', {
        driver = new HtmlUnitDriver();
        driver.get("http://localhost:8080");
        element = driver.findElement(By.linkText("register new user"));       
        element.click();       
    }
    when 'a too sort username and valid password are entered', {
        element = driver.findElement(By.name("username"));
        element.sendKeys("v1");
        element = driver.findElement(By.name("password"));
        element.sendKeys("12345678");
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys("12345678");
        element = driver.findElement(By.name("add"));
        element.submit();
    }
    then 'new user is not be registered to system', {
        driver.getPageSource().contains("Create username and give password").shouldBe true
    }
}

scenario "creation fails with already taken username and valid pasword", {
    given 'command new user is selected', {
        driver = new HtmlUnitDriver();
        driver.get("http://localhost:8080");
        element = driver.findElement(By.linkText("register new user"));       
        element.click();       
    }
    when 'a already taken username and valid password are entered', {
        element = driver.findElement(By.name("username"));
        element.sendKeys("pekka");
        element = driver.findElement(By.name("password"));
        element.sendKeys("12345678");
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys("12345678");
        element = driver.findElement(By.name("add"));
        element.submit();
    }
    then 'new user is not be registered to system', {
        driver.getPageSource().contains("Create username and give password").shouldBe true
    }
}

scenario "can not login with account that is not succesfully created", {
    given 'command new user is selected', {
        driver = new HtmlUnitDriver();
        driver.get("http://localhost:8080");
        element = driver.findElement(By.linkText("login"));       
        element.click();       
    }
    when 'a invalid username/password are entered', {
        element = driver.findElement(By.name("username"));
        element.sendKeys("v1");
        element = driver.findElement(By.name("password"));
        element.sendKeys("12345678");
        element = driver.findElement(By.name("login"));
        element.submit();
    }
    then  'new credentials do not allow logging in to system', {
        driver.getPageSource().contains("Give your credentials to login").shouldBe true
    }
}