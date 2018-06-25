from selenium import webdriver
from selenium.webdriver.chrome.options import Options

# configure options
options = Options()
options.add_argument('--headless')
options.add_argument('--disable-gpu')  # Last I checked this was necessary.

CHROMEDRIVER_PATH = 'D:\Selenium\chromedriver_win32\chromedriver.exe'

driver = webdriver.Chrome(CHROMEDRIVER_PATH, chrome_options=options)
driver.get("https://time.is/");
driver.maximize_window()

print driver.page_source
driver.quit()