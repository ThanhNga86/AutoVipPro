/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.autovp;

import java.awt.Color;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.SwingWorker;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 *
 * @author Lenovo
 */
public class AutoMB_4 extends javax.swing.JFrame {

    private final String urlWeb = "https://tr8d33.xoso66.expert/home/#/lottery?tabName=Lottery&id=47";
    private WebDriver driver = null;
    private ScheduledExecutorService scheduler = null;
    private final NumberFormat numb = NumberFormat.getCurrencyInstance(new Locale("Vi", "VN"));
    private final Color color_green = new Color(33, 152, 45);
    private final Color color_red = new Color(152, 33, 33);
    private String issue = "";
    private int countId = 0;
    private int tongLai = 0;
    private String xoso66_1_data = "";
    private String xoso66_2_data = "";
    private String cau_1 = "";
    private String cau_2 = "";
    private String bet_1 = "";
    private String bet_2 = "";
    private final int countData = 1920;
    private int countStop = 0;
    private int countBet_1 = 0;
    private int countBet_2 = 0;
    private int countBetReal_1 = 0;
    private int countBetReal_2 = 0;
    private boolean flagXoso66 = false;
    private boolean flagBet_1 = false;
    private boolean flagBet_2 = false;
    private int countWait_1 = 0;
    private int countWait_2 = 0;
    private final Integer[] betLevel_1 = {12500, 25000, 50000, 100000, 200000};
    private final Integer[] betLevel_2 = {12500, 25000, 50000, 100000, 200000};
    private final Integer[] betLevelReal_1 = {10000, 20000, 40000, 80000, 160000};
    private final Integer[] betLevelReal_2 = {10000, 20000, 40000, 80000, 160000};
    private final String arrCau_1[] = new String[]{
        "TTTNT",
        "NNNTT",
        "NNNTN",
        "TNNNT",
        "TTTNN",
        "TNTTT",
        "NTTTN",
        "NTNNN"
    };
    private final String arrCau_2[] = new String[]{
        "TTTNT",
        "NNNTT",
        "NNNTN",
        "TNNNT",
        "TTTNN",
        "TNTTT",
        "NTTTN",
        "NTNNN"
    };
   
    public AutoMB_4() {
        initComponents();
        setTitle("AutoMB_4");
        setResizable(false);
        setLocationRelativeTo(null);
        ImageIcon logoIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\com\\autovp\\repo\\logo.png");
        setIconImage(logoIcon.getImage());

        xoso66_1_setData.setLineWrap(true);
        xoso66_1_setData.setWrapStyleWord(true);
        xoso66_1_setData.disable();
        xoso66_2_setData.setLineWrap(true);
        xoso66_2_setData.setWrapStyleWord(true);
        xoso66_2_setData.disable();
    }

    private void xoso66_login() {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.cssSelector(".x-btn")));
//            List<WebElement> els = driver.findElements(By.cssSelector(".el-input__inner"));
//            WebElement tk = els.get(0);
//            tk.sendKeys(xoso_TK.getText().trim());
//            WebElement mk = els.get(1);
//            mk.sendKeys(xoso_MK.getText().trim());
//            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.cssSelector(".el-button.btn-login.el-button--default")));
//            Thread.sleep(3000);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.cssSelector(".el-button.btn-guestLogin.el-button--default")));
            Thread.sleep(3000);

            // tat thong bao
            WebElement wapper = driver.findElement(By.cssSelector(".el-dialog__wrapper.announce-dialog"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", wapper.findElement(By.cssSelector(".el-dialog__headerbtn")));
            Thread.sleep(3000);

            countId = 0;
            cauTX_1.setText("Cầu: " + rdCauTX_1());
            lanDatTiepTheo_1.setText("Lần đặt tiếp theo: " + (countBetReal_1 + 1));
            soTienDat_1.setText("Số tiền: " + numb.format(betLevelReal_1[countBetReal_1]));

            cauTX_2.setText("Cầu: " + rdCauTX_2());
            lanDatTiepTheo_2.setText("Lần đặt tiếp theo: " + (countBetReal_2 + 1));
            soTienDat_2.setText("Số tiền: " + numb.format(betLevelReal_2[countBetReal_2]));

            WebElement playSelectBox = driver.findElement(By.cssSelector(".playSelectBox"));
            WebElement tabs = playSelectBox.findElement(By.cssSelector(".el-tabs__content"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tabs.findElements(By.cssSelector("li")).get(4));
        } catch (Exception e) {
            e.printStackTrace();
        }

        xoso66_loop();
    }

    private void checkLogin() {
        try {
            WebElement checkButton = driver.findElement(By.cssSelector(".el-button.btn-login.el-button--default"));
            if (checkButton.isDisplayed() && checkButton.isEnabled()) {
                scheduler.shutdown();
                scheduler = null;
                driver.get(urlWeb);
                driver.navigate().refresh();
                Thread.sleep(2000);
                xoso66_login();
                System.out.println("Login again !");
            }
        } catch (Exception e) {
        }
    }

    private void xoso66_loop() {
        try {
            scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(() -> {
                try {
                    Map<String, String> result = xoso66_getData();
                    String id = result.get("id") != null ? result.get("id") : "";
                    if (!issue.equals(id)) {
                        countStop += 1;
                        countWait_1 += 1;
                        labelTimeNext_1.setText(countWait_1 <= 0 ? countWait_1 + "" : "-");
                        countWait_2 += 1;
                        labelTimeNext_2.setText(countWait_2 <= 0 ? countWait_2 + "" : "-");
                        countId = 0;

                        issue = id;
                        xoso66_1_data += (result.get("kqTX") == null) ? "" : result.get("kqTX");
                        xoso66_2_data += (result.get("kqCL") == null) ? "" : result.get("kqCL");
                        xoso66_1_data = processData(xoso66_1_data, 18);
                        xoso66_1_setData.setText(xoso66_1_data);
                        xoso66_2_data = processData(xoso66_2_data, 18);
                        xoso66_2_setData.setText(xoso66_2_data);

                        // ket qua cau 3 - 4 - 5 TX
                        List<String> results_1 = findClosestPatterns_1(xoso66_1_data);
                        if (!results_1.get(0).equals("-")) {
                            xoso66_1_cau3.setText(results_1.get(0));
                        }
                        if (!results_1.get(1).equals("-")) {
                            xoso66_1_cau4.setText(results_1.get(1));
                        }
                        if (!results_1.get(2).equals("-")) {
                            xoso66_1_cau5.setText(results_1.get(2));
                        }

                        // ket qua cau 3 - 4 - 5 CL
                        List<String> results_2 = findClosestPatterns_2(xoso66_2_data);
                        if (!results_2.get(0).equals("-")) {
                            xoso66_2_cau3.setText(results_2.get(0));
                        }
                        if (!results_2.get(1).equals("-")) {
                            xoso66_2_cau4.setText(results_2.get(1));
                        }
                        if (!results_2.get(2).equals("-")) {
                            xoso66_2_cau5.setText(results_2.get(2));
                        }

                        // Kiem tra thang hoac thua neu co dat cuoc TX
                        if (!bet_1.isEmpty()) {
                            if (bet_1.equals(result.get("kqTX"))) { // thua
                                if (countBet_1 == betLevel_1.length - 5) {
                                    countBetReal_1 += 1;
                                    if (countBetReal_1 < betLevelReal_1.length) {
                                    } else {
                                        for (int i = 0; i < betLevelReal_1.length; i++) {
                                            tongLai -= betLevelReal_1[i];
                                        }
                                        countBetReal_1 = 0;
                                        labelTongLai.setText(numb.format(tongLai));
                                    }
                                    lanDatTiepTheo_1.setText("Lần đặt tiếp theo: " + (countBetReal_1 + 1));
                                    soTienDat_1.setText("Số tiền: " + numb.format(betLevelReal_1[countBetReal_1]));
                                }

                                countBet_1 = 0;
                                cauTX_1.setText("Cầu: " + rdCauTX_1());
                            } else { // thang
                                if (countBet_1 == betLevel_1.length - 5) {
                                    tongLai += betLevelReal_1[0];
                                    countBetReal_1 = 0;
                                    labelTongLai.setText(numb.format(tongLai));
                                    lanDatTiepTheo_1.setText("Lần đặt tiếp theo: " + (countBetReal_1 + 1));
                                    soTienDat_1.setText("Số tiền: " + numb.format(betLevelReal_1[countBetReal_1]));

                                    int rdWait = new Random().nextInt(20) + 10;
                                    countWait_1 = -rdWait;
                                    labelTimeNext_1.setText(countWait_1 + "");
                                }

                                countBet_1 += 1;
                                if (countBet_1 < betLevel_1.length) {
                                } else {
                                    countBet_1 = 0;
                                    cau_1 = "";
                                    cauTX_1.setText("Cầu: " + rdCauTX_1());
                                }
                            }

                            datCuoc_1.setText("Đặt cược: -");
                            bet_1 = "";
                        }

                        // Kiem tra thang hoac thua neu co dat cuoc CL
                        if (!bet_2.isEmpty()) {
                            if (bet_2.equals(result.get("kqCL"))) { // thua
                                if (countBet_2 == betLevel_2.length - 5) {
                                    countBetReal_2 += 1;
                                    if (countBetReal_2 < betLevelReal_2.length) {
                                    } else {
                                        for (int i = 0; i < betLevelReal_2.length; i++) {
                                            tongLai -= betLevelReal_2[i];
                                        }
                                        countBetReal_2 = 0;
                                        labelTongLai.setText(numb.format(tongLai));
                                    }
                                    lanDatTiepTheo_2.setText("Lần đặt tiếp theo: " + (countBetReal_2 + 1));
                                    soTienDat_2.setText("Số tiền: " + numb.format(betLevelReal_2[countBetReal_2]));
                                }

                                countBet_2 = 0;
                                cauTX_2.setText("Cầu: " + rdCauTX_2());
                            } else { // thang
                                if (countBet_2 == betLevel_2.length - 5) {
                                    tongLai += betLevelReal_2[0];
                                    countBetReal_2 = 0;
                                    labelTongLai.setText(numb.format(tongLai));
                                    lanDatTiepTheo_2.setText("Lần đặt tiếp theo: " + (countBetReal_2 + 1));
                                    soTienDat_2.setText("Số tiền: " + numb.format(betLevelReal_2[countBetReal_2]));

                                    int rdWait = new Random().nextInt(20) + 10;
                                    countWait_2 = -rdWait;
                                    labelTimeNext_2.setText(countWait_2 + "");
                                }

                                countBet_2 += 1;
                                if (countBet_2 < betLevel_2.length) {
                                } else {
                                    countBet_2 = 0;
                                    cau_2 = "";
                                    cauTX_2.setText("Cầu: " + rdCauTX_2());
                                }
                            }

                            datCuoc_2.setText("Đặt cược: -");
                            bet_2 = "";
                        }

                        // doi mau red hoac green khi thay doi dong` tien`
                        labelTongLai.setText(numb.format(tongLai));
                        if (tongLai < 0) {
                            labelTongLai.setForeground(color_red);
                        } else {
                            labelTongLai.setForeground(color_green);
                        }

                        xoso66_stopToWin();
                        xoso66_nextBet();
                    } else {
                        // error: neu rot mang ko chay duoc thi reload lai trang
                        try {
                            countId += 1;
                            if (countId > 5) {
                                driver.navigate().refresh();
                                Thread.sleep(3000);
                                countId = 0;
                                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.cssSelector(".x-btn")));
                                WebElement playSelectBox = driver.findElement(By.cssSelector(".playSelectBox"));
                                WebElement tabs = playSelectBox.findElement(By.cssSelector(".el-tabs__content"));
                                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tabs.findElements(By.cssSelector("li")).get(4));
                            }
                        } catch (Exception e) {
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Kiem tra neu bi logout thi login lai
                checkLogin();
            }, 0, 11, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void xoso66_nextBet() {
        if (countWait_1 > 0 && flagBet_1 == false) {
            List<String> results = List.of(xoso66_1_cau3.getText(), xoso66_1_cau4.getText(), xoso66_1_cau5.getText());

            Map<String, String> map = new HashMap<>();
            map.put("XTTTXT", "3,1");
            map.put("TXXXTX", "3,1");
            map.put("XTTTXX", "3,2");
            map.put("TXXXTT", "3,2");
            map.put("XTTTTXT", "4,1");
            map.put("TXXXXTX", "4,1");
            map.put("XTTTTXX", "4,2");
            map.put("TXXXXTT", "4,2");
            map.put("XTTTTTXT", "5,1");
            map.put("TXXXXXTX", "5,1");
            map.put("XTTTTTXX", "5,2");
            map.put("TXXXXXTT", "5,2");

            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                String keySub = key.substring(0, key.length() - 1);
                if (xoso66_1_data.endsWith(keySub) && results.contains(value)) {
                    bet_1 = key.charAt(key.length() - 1) + "";
                    break;
                }
            }
        }

        if (countWait_2 > 0 && flagBet_2 == false) {
            List<String> results = List.of(xoso66_2_cau3.getText(), xoso66_2_cau4.getText(), xoso66_2_cau5.getText());

            Map<String, String> map = new HashMap<>();
            map.put("CLLLCL", "3,1");
            map.put("LCCCLC", "3,1");
            map.put("CLLLCC", "3,2");
            map.put("LCCCLL", "3,2");
            map.put("CLLLLCL", "4,1");
            map.put("LCCCCLC", "4,1");
            map.put("CLLLLCC", "4,2");
            map.put("LCCCCLL", "4,2");
            map.put("CLLLLLCL", "5,1");
            map.put("LCCCCCLC", "5,1");
            map.put("CLLLLLCC", "5,2");
            map.put("LCCCCCLL", "5,2");
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                String keySub = key.substring(0, key.length() - 1);
                if (xoso66_2_data.endsWith(keySub) && results.contains(value)) {
                    bet_2 = key.charAt(key.length() - 1) + "";
                    break;
                }
            }
        }

        // Dat cuoc
        String betReal_1 = "";
        if (!bet_1.isEmpty()) {
            String cauTN_1 = this.cau_1.charAt(countBet_1) + "";
            soTienDat_1.setText("Số tiền: " + numb.format(betLevelReal_1[countBetReal_1]));
            lanDatTiepTheo_1.setText("Lần đặt tiếp theo: " + String.valueOf(countBetReal_1 + 1));
            // Dat theo cau thuan va nghich
            if (cauTN_1.equals("N")) {
                bet_1 = bet_1.equals("T") ? "X" : "T";
            }

            if (countBet_1 == betLevel_1.length - 5) {
                betReal_1 = bet_1.equals("T") ? "X" : "T";
                datCuoc_1.setText("Đặt cược: " + betReal_1);
            }
        }

        String betReal_2 = "";
        if (!bet_2.isEmpty()) {
            String cauTN_2 = this.cau_2.charAt(countBet_2) + "";
            soTienDat_2.setText("Số tiền: " + numb.format(betLevelReal_2[countBetReal_2]));
            lanDatTiepTheo_2.setText("Lần đặt tiếp theo: " + String.valueOf(countBetReal_2 + 1));
            // Dat theo cau thuan va nghich
            if (cauTN_2.equals("N")) {
                bet_2 = bet_2.equals("L") ? "C" : "L";
            }

            if (countBet_2 == betLevel_2.length - 5) {
                betReal_2 = bet_2.equals("L") ? "C" : "L";
                datCuoc_2.setText("Đặt cược: " + betReal_2);
            }
        }

        if (!betReal_1.isEmpty() || !betReal_2.isEmpty()) {
            xoso66_pick(betReal_1, String.valueOf(betLevelReal_1[countBetReal_1]), betReal_2, String.valueOf(betLevelReal_2[countBetReal_2]));
        }
    }

    private void xoso66_pick(String pick_1, String price_1, String pick_2, String price_2) {
        try {
            WebElement funPlayBet = driver.findElement(By.cssSelector(".funPlayBet"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", funPlayBet);
            Thread.sleep(200);

            WebElement ballBox = funPlayBet.findElement(By.cssSelector(".ballBox"));
            List<WebElement> flex1 = ballBox.findElements(By.cssSelector(".flex1"));

            WebElement pickElement_1 = null;
            WebElement pickElement_2 = null;
            if (!pick_1.isEmpty()) {
                // Chọn T hoặc X
                pickElement_1 = pick_1.equals("T") ? flex1.get(0) : flex1.get(1);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", pickElement_1);
                Thread.sleep(1000);

                // Nhập số tiền
                WebElement order = driver.findElement(By.cssSelector(".my-order-list"));
                WebElement orderItem = order.findElements(By.cssSelector(".order-item-wp")).get(0);
                WebElement inpPrice = orderItem.findElement(By.cssSelector("input[type='tel']"));
                inpPrice.sendKeys(Keys.CONTROL + "a");
                inpPrice.sendKeys(Keys.DELETE);
                Thread.sleep(100);
                inpPrice.sendKeys(price_1);
                Thread.sleep(100);
            }

            if (!pick_2.isEmpty()) {
                // Chọn T hoặc X
                pickElement_2 = pick_2.equals("L") ? flex1.get(2) : flex1.get(3);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", pickElement_2);
                Thread.sleep(1000);

                // Nhập số tiền
                WebElement order = driver.findElement(By.cssSelector(".my-order-list"));
                WebElement orderItem = order.findElements(By.cssSelector(".order-item-wp")).get(0);
                WebElement inpPrice = orderItem.findElement(By.cssSelector("input[type='tel']"));
                inpPrice.sendKeys(Keys.CONTROL + "a");
                inpPrice.sendKeys(Keys.DELETE);
                Thread.sleep(100);
                inpPrice.sendKeys(price_2);
                Thread.sleep(100);
            }

            // Đặt cược
            WebElement betButton = driver.findElement(By.cssSelector(".el-button.bet-btn.el-button--primary"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", betButton);
            Thread.sleep(2000);

            // Nếu đã click đặt cược mà không được, click thêm lần nữa
            try {
                WebElement checkButton = driver.findElement(By.cssSelector(".el-button.bet-btn.el-button--primary"));
                if (checkButton.isDisplayed() && checkButton.isEnabled()) {
                    if (pickElement_1 != null) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", pickElement_1);
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", pickElement_1);
                        Thread.sleep(1000);
                        WebElement order = driver.findElement(By.cssSelector(".my-order-list"));
                        WebElement orderItem = order.findElements(By.cssSelector(".order-item-wp")).get(0);
                        WebElement inpPrice = orderItem.findElement(By.cssSelector("input[type='tel']"));
                        inpPrice.sendKeys(Keys.CONTROL + "a");
                        inpPrice.sendKeys(Keys.DELETE);
                        Thread.sleep(100);
                        inpPrice.sendKeys(price_1);
                        Thread.sleep(100);
                    }

                    if (pickElement_2 != null) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", pickElement_2);
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", pickElement_2);
                        Thread.sleep(1000);
                        WebElement order = driver.findElement(By.cssSelector(".my-order-list"));
                        WebElement orderItem = order.findElements(By.cssSelector(".order-item-wp")).get(0);
                        WebElement inpPrice = orderItem.findElement(By.cssSelector("input[type='tel']"));
                        inpPrice.sendKeys(Keys.CONTROL + "a");
                        inpPrice.sendKeys(Keys.DELETE);
                        Thread.sleep(100);
                        inpPrice.sendKeys(price_2);
                        Thread.sleep(100);
                    }

                    WebElement betNext = driver.findElement(By.cssSelector(".el-button.bet-btn.el-button--primary"));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", betNext);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", betNext);
                }
            } catch (Exception e) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void xoso66_stopToWin() {
        int tongReal = 0;
        for (Integer money : betLevelReal_2) {
            tongReal += money;
        }

        if (tongLai >= (tongReal * 2) && countBetReal_1 == 0 && countBetReal_2 == 0) {
            xoso66_onOff.setText("Done");
            scheduler.shutdown();
            flagXoso66 = false;
            flagBet_1 = true;
            flagBet_2 = true;
        } else if (tongLai >= (tongReal * 2) && countBetReal_1 == 0 && countBetReal_2 > 0) {
            flagBet_1 = true;
        } else if (tongLai >= (tongReal * 2) && countBetReal_1 > 0 && countBetReal_2 == 0) {
            flagBet_2 = true;
        } else if (tongLai <= (tongReal * -1.6) && countBetReal_1 == 0 && countBetReal_2 == 0) {
            xoso66_onOff.setText("Done");
            scheduler.shutdown();
            flagXoso66 = false;
            flagBet_1 = true;
            flagBet_2 = true;
        } else if (tongLai <= (tongReal * -1.6) && countBetReal_1 == 0 && countBetReal_2 > 0) {
            flagBet_1 = true;
        } else if (tongLai <= (tongReal * -1.6) && countBetReal_1 > 0 && countBetReal_2 == 0) {
            flagBet_2 = true;
        } else if (countStop >= (countData - 120) && countBetReal_1 == 0 && countBetReal_2 == 0) {
            xoso66_onOff.setText("Done");
            scheduler.shutdown();
            flagXoso66 = false;
            flagBet_1 = true;
            flagBet_2 = true;
        } else if (countStop >= (countData - 120) && countBetReal_1 == 0 && countBetReal_2 > 0) {
            flagBet_1 = true;
        } else if (countStop >= (countData - 120) && countBetReal_1 > 0 && countBetReal_2 == 0) {
            flagBet_2 = true;
        } else {
            flagBet_1 = false;
            flagBet_2 = false;
        }
    }

    private Map<String, String> xoso66_getData() {
        Map<String, String> map = new HashMap<>();
        try {
            Thread.sleep(1000);
            WebElement _openNumber = driver.findElement(By.cssSelector("._openNumber"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", _openNumber);
            String issue = _openNumber.findElement(By.cssSelector("h2")).getText().replace("Kỳ", "").replace(", giải đặc biệt", "").trim();
            WebElement resultNumber = _openNumber.findElements(By.cssSelector("span")).get(3);
            map.put("id", issue);

            int result = Integer.parseInt(resultNumber.getText().trim());
            if (result >= 5) {
                map.put("kqTX", "T");
            } else {
                map.put("kqTX", "X");
            }

            if (result % 2 == 0) {
                map.put("kqCL", "C");
            } else {
                map.put("kqCL", "L");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // tat thong bao win
        try {
            WebElement notice = driver.findElement(By.cssSelector(".winning-notice-wp"));
            if (notice.isDisplayed() && notice.isEnabled()) {
                notice.findElement(By.cssSelector(".iconfont.icon-icon_close_white")).click();
            }
        } catch (Exception e) {
        }
        return map;
    }

    private String rdCauTX_1() {
        Random random = new Random();

        while (true) {
            int rd = random.nextInt(arrCau_1.length) + 0;

            if (!cau_1.equals(arrCau_1[rd])) {
                cau_1 = arrCau_1[rd];
                break;
            } else if (arrCau_1.length <= 1) {
                break;
            }
        }
        return cau_1;
    }

    private String rdCauTX_2() {
        Random random = new Random();

        while (true) {
            int rd = random.nextInt(arrCau_2.length) + 0;

            if (!cau_2.equals(arrCau_2[rd])) {
                cau_2 = arrCau_2[rd];
                break;
            } else if (arrCau_2.length <= 1) {
                break;
            }
        }
        return cau_2;
    }

    private String processData(String data, int maxCau) {
        // Biến tạm để lưu chuỗi xử lý hiện tại
        StringBuilder currentData = new StringBuilder();
        currentData.append(data.charAt(0));

        // Biến tạm để lưu danh sách các cầu
        LinkedList<String> cauList = new LinkedList<>();

        // Duyệt qua chuỗi từ vị trí thứ 2
        for (int i = 1; i < data.length(); i++) {
            // Nếu ký tự hiện tại giống ký tự trước đó, thêm vào cầu hiện tại
            if (data.charAt(i) == data.charAt(i - 1)) {
                currentData.append(data.charAt(i));
            } else {
                // Nếu có cầu mới, thêm vào danh sách
                addCau(cauList, currentData.toString(), maxCau);

                // Tạo cầu mới với ký tự hiện tại
                currentData = new StringBuilder();
                currentData.append(data.charAt(i));
            }
        }

        // Thêm cầu cuối cùng vào danh sách
        addCau(cauList, currentData.toString(), maxCau);

        // Kết hợp lại các cầu để tạo ra chuỗi mới
        return String.join("", cauList);
    }

    private void addCau(LinkedList<String> cauList, String cau, int maxCau) {
        // Nếu danh sách đã đủ số lượng cầu tối đa, xóa cầu đầu tiên
        if (cauList.size() >= maxCau) {
            cauList.removeFirst();
        }
        // Thêm cầu mới vào danh sách
        cauList.add(cau);
    }

    private List<String> findClosestPatterns_1(String mainPattern) {
        // Các mẫu và giá trị tương ứng cho mỗi nhóm pattern
        String[][] patterns3 = {{"XTTTXT", "3,1"}, {"TXXXTX", "3,1"}, {"XTTTXX", "3,2"}, {"TXXXTT", "3,2"}};
        String[][] patterns4 = {{"XTTTTXT", "4,1"}, {"TXXXXTX", "4,1"}, {"XTTTTXX", "4,2"}, {"TXXXXTT", "4,2"}};
        String[][] patterns5 = {{"XTTTTTXT", "5,1"}, {"TXXXXXTX", "5,1"}, {"XTTTTTXX", "5,2"}, {"TXXXXXTT", "5,2"}};

        List<String> closestPatterns = new ArrayList<>();

        // Tìm cầu gần nhất cho patterns3
        String closestPattern3 = "";
        int closestIndex3 = -1;
        for (String[] pattern : patterns3) {
            int index = mainPattern.indexOf(pattern[0]);
            while (index != -1) {
                if (closestIndex3 == -1 || index > closestIndex3) {
                    closestPattern3 = pattern[1]; // Lưu giá trị con số tương ứng
                    closestIndex3 = index;
                }
                index = mainPattern.indexOf(pattern[0], index + 1);
            }
        }
        closestPatterns.add(closestPattern3.isEmpty() ? "-" : closestPattern3);

        // Tìm cầu gần nhất cho patterns4
        String closestPattern4 = "";
        int closestIndex4 = -1;
        for (String[] pattern : patterns4) {
            int index = mainPattern.indexOf(pattern[0]);
            while (index != -1) {
                if (closestIndex4 == -1 || index > closestIndex4) {
                    closestPattern4 = pattern[1]; // Lưu giá trị con số tương ứng
                    closestIndex4 = index;
                }
                index = mainPattern.indexOf(pattern[0], index + 1);
            }
        }
        closestPatterns.add(closestPattern4.isEmpty() ? "-" : closestPattern4);

        // Tìm cầu gần nhất cho patterns5
        String closestPattern5 = "";
        int closestIndex5 = -1;
        for (String[] pattern : patterns5) {
            int index = mainPattern.indexOf(pattern[0]);
            while (index != -1) {
                if (closestIndex5 == -1 || index > closestIndex5) {
                    closestPattern5 = pattern[1]; // Lưu giá trị con số tương ứng
                    closestIndex5 = index;
                }
                index = mainPattern.indexOf(pattern[0], index + 1);
            }
        }
        closestPatterns.add(closestPattern5.isEmpty() ? "-" : closestPattern5);

        return closestPatterns; // Trả về danh sách các kết quả con số
    }

    private List<String> findClosestPatterns_2(String mainPattern) {
        // Các mẫu và giá trị tương ứng cho mỗi nhóm pattern
        String[][] patterns3 = {{"CLLLCL", "3,1"}, {"LCCCLC", "3,1"}, {"CLLLCC", "3,2"}, {"LCCCLL", "3,2"}};
        String[][] patterns4 = {{"CLLLLCL", "4,1"}, {"LCCCCLC", "4,1"}, {"CLLLLCC", "4,2"}, {"LCCCCLL", "4,2"}};
        String[][] patterns5 = {{"CLLLLLCL", "5,1"}, {"LCCCCCLC", "5,1"}, {"CLLLLLCC", "5,2"}, {"LCCCCCLL", "5,2"}};

        List<String> closestPatterns = new ArrayList<>();

        // Tìm cầu gần nhất cho patterns3
        String closestPattern3 = "";
        int closestIndex3 = -1;
        for (String[] pattern : patterns3) {
            int index = mainPattern.indexOf(pattern[0]);
            while (index != -1) {
                if (closestIndex3 == -1 || index > closestIndex3) {
                    closestPattern3 = pattern[1]; // Lưu giá trị con số tương ứng
                    closestIndex3 = index;
                }
                index = mainPattern.indexOf(pattern[0], index + 1);
            }
        }
        closestPatterns.add(closestPattern3.isEmpty() ? "-" : closestPattern3);

        // Tìm cầu gần nhất cho patterns4
        String closestPattern4 = "";
        int closestIndex4 = -1;
        for (String[] pattern : patterns4) {
            int index = mainPattern.indexOf(pattern[0]);
            while (index != -1) {
                if (closestIndex4 == -1 || index > closestIndex4) {
                    closestPattern4 = pattern[1]; // Lưu giá trị con số tương ứng
                    closestIndex4 = index;
                }
                index = mainPattern.indexOf(pattern[0], index + 1);
            }
        }
        closestPatterns.add(closestPattern4.isEmpty() ? "-" : closestPattern4);

        // Tìm cầu gần nhất cho patterns5
        String closestPattern5 = "";
        int closestIndex5 = -1;
        for (String[] pattern : patterns5) {
            int index = mainPattern.indexOf(pattern[0]);
            while (index != -1) {
                if (closestIndex5 == -1 || index > closestIndex5) {
                    closestPattern5 = pattern[1]; // Lưu giá trị con số tương ứng
                    closestIndex5 = index;
                }
                index = mainPattern.indexOf(pattern[0], index + 1);
            }
        }
        closestPatterns.add(closestPattern5.isEmpty() ? "-" : closestPattern5);

        return closestPatterns; // Trả về danh sách các kết quả con số
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        xoso66_onOff = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        labelTongLai = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        xoso_TK = new javax.swing.JTextField();
        xoso_MK = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        xoso66_1_cau3 = new javax.swing.JLabel();
        xoso66_1_cau4 = new javax.swing.JLabel();
        xoso66_1_cau5 = new javax.swing.JLabel();
        cauTX_1 = new javax.swing.JLabel();
        lanDatTiepTheo_1 = new javax.swing.JLabel();
        soTienDat_1 = new javax.swing.JLabel();
        datCuoc_1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        labelTimeNext_1 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        xoso66_1_setData = new javax.swing.JTextArea();
        xoso66_2_cau3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        xoso66_2_setData = new javax.swing.JTextArea();
        xoso66_2_cau4 = new javax.swing.JLabel();
        xoso66_2_cau5 = new javax.swing.JLabel();
        cauTX_2 = new javax.swing.JLabel();
        soTienDat_2 = new javax.swing.JLabel();
        lanDatTiepTheo_2 = new javax.swing.JLabel();
        datCuoc_2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        labelTimeNext_2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        xoso66_onOff.setBackground(new java.awt.Color(51, 153, 255));
        xoso66_onOff.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        xoso66_onOff.setText("Bắt đầu");
        xoso66_onOff.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                xoso66_onOffMousePressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel3.setText("Tổng lãi:");

        labelTongLai.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        labelTongLai.setForeground(new java.awt.Color(33, 152, 45));
        labelTongLai.setText("0đ");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Tài khoản");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Mật khẩu");

        xoso66_1_cau3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        xoso66_1_cau3.setText("-");

        xoso66_1_cau4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        xoso66_1_cau4.setText("-");

        xoso66_1_cau5.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        xoso66_1_cau5.setText("-");

        cauTX_1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cauTX_1.setText("Cầu: -");

        lanDatTiepTheo_1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lanDatTiepTheo_1.setText("Lần đặt tiếp theo: -");

        soTienDat_1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        soTienDat_1.setText("Số tiền: -");

        datCuoc_1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        datCuoc_1.setText("Đặt cược: -");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel5.setText("Thời gian đặt tiếp theo: ");

        labelTimeNext_1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        labelTimeNext_1.setText("-");

        xoso66_1_setData.setColumns(20);
        xoso66_1_setData.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        xoso66_1_setData.setForeground(new java.awt.Color(102, 102, 255));
        xoso66_1_setData.setRows(5);
        jScrollPane2.setViewportView(xoso66_1_setData);

        xoso66_2_cau3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        xoso66_2_cau3.setText("-");

        xoso66_2_setData.setColumns(20);
        xoso66_2_setData.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        xoso66_2_setData.setForeground(new java.awt.Color(102, 102, 255));
        xoso66_2_setData.setRows(5);
        jScrollPane3.setViewportView(xoso66_2_setData);

        xoso66_2_cau4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        xoso66_2_cau4.setText("-");

        xoso66_2_cau5.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        xoso66_2_cau5.setText("-");

        cauTX_2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cauTX_2.setText("Cầu: -");

        soTienDat_2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        soTienDat_2.setText("Số tiền: -");

        lanDatTiepTheo_2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lanDatTiepTheo_2.setText("Lần đặt tiếp theo: -");

        datCuoc_2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        datCuoc_2.setText("Đặt cược: -");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel6.setText("Thời gian đặt tiếp theo: ");

        labelTimeNext_2.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        labelTimeNext_2.setText("-");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(jSeparator2)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelTongLai, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(xoso_TK, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(xoso_MK, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(xoso66_onOff, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cauTX_1)
                                    .addComponent(lanDatTiepTheo_1)
                                    .addComponent(datCuoc_1)
                                    .addComponent(soTienDat_1)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelTimeNext_1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(xoso66_1_cau3)
                                .addGap(18, 18, 18)
                                .addComponent(xoso66_1_cau4)
                                .addGap(18, 18, 18)
                                .addComponent(xoso66_1_cau5)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(12, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(xoso66_2_cau3)
                        .addGap(18, 18, 18)
                        .addComponent(xoso66_2_cau4)
                        .addGap(18, 18, 18)
                        .addComponent(xoso66_2_cau5))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cauTX_2)
                            .addComponent(lanDatTiepTheo_2)
                            .addComponent(datCuoc_2)
                            .addComponent(soTienDat_2)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelTimeNext_2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(28, 28, 28))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(xoso66_onOff, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(xoso_TK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(labelTongLai)
                        .addComponent(xoso_MK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(xoso66_1_cau3)
                            .addComponent(xoso66_1_cau4)
                            .addComponent(xoso66_1_cau5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cauTX_1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lanDatTiepTheo_1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(soTienDat_1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(datCuoc_1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(labelTimeNext_1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(xoso66_2_cau3)
                            .addComponent(xoso66_2_cau4)
                            .addComponent(xoso66_2_cau5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cauTX_2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lanDatTiepTheo_2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(soTienDat_2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(datCuoc_2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3)
                            .addComponent(labelTimeNext_2))))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void xoso66_onOffMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_xoso66_onOffMousePressed
        if (flagXoso66 == false) {
            xoso66_onOff.setText("Tắt");
            flagXoso66 = true;
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    ChromeOptions options = new ChromeOptions();
                    // Bật chế độ ẩn danh (Incognito)
                    options.addArguments("--incognito");
                    // Tắt tiếng trình duyệt
                    options.addArguments("--mute-audio");
                    driver = new ChromeDriver(options);
                    driver.get(urlWeb);

                    xoso66_login();
                    return null;
                }

            }.execute(); // Khởi chạy SwingWorke
        } else {
            xoso66_onOff.setText("Bắt đầu");
            driver.quit();
            scheduler.shutdown();
            driver = null;
            flagXoso66 = false;
        }
    }//GEN-LAST:event_xoso66_onOffMousePressed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AutoMB_4.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AutoMB_4.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AutoMB_4.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AutoMB_4.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AutoMB_4().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel cauTX_1;
    private javax.swing.JLabel cauTX_2;
    private javax.swing.JLabel datCuoc_1;
    private javax.swing.JLabel datCuoc_2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel labelTimeNext_1;
    private javax.swing.JLabel labelTimeNext_2;
    private javax.swing.JLabel labelTongLai;
    private javax.swing.JLabel lanDatTiepTheo_1;
    private javax.swing.JLabel lanDatTiepTheo_2;
    private javax.swing.JLabel soTienDat_1;
    private javax.swing.JLabel soTienDat_2;
    private javax.swing.JLabel xoso66_1_cau3;
    private javax.swing.JLabel xoso66_1_cau4;
    private javax.swing.JLabel xoso66_1_cau5;
    private javax.swing.JTextArea xoso66_1_setData;
    private javax.swing.JLabel xoso66_2_cau3;
    private javax.swing.JLabel xoso66_2_cau4;
    private javax.swing.JLabel xoso66_2_cau5;
    private javax.swing.JTextArea xoso66_2_setData;
    private javax.swing.JButton xoso66_onOff;
    private javax.swing.JPasswordField xoso_MK;
    private javax.swing.JTextField xoso_TK;
    // End of variables declaration//GEN-END:variables
}
