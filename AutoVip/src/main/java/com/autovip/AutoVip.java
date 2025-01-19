/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.autovip;

import java.awt.Color;
import java.io.File;
import java.text.NumberFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class AutoVip extends javax.swing.JFrame {

    private final String urlWeb = "https://tr8d33.xoso66.expert/home/#/";
    private WebDriver driver = null;
    private final NumberFormat numb = NumberFormat.getCurrencyInstance(new Locale("Vi", "VN"));
    private ScheduledExecutorService scheduler = null;
    private final Color color_green = new Color(33, 152, 45);
    private final Color color_red = new Color(152, 33, 33);
    private final String filePath = "C:\\DBAuto";
    private File fileTongLaiReal;
    private File fileTongLai;
    private File fileCountBetVip;
    private File fileCountBetPro;
    private int tamLai = 0;
    private boolean flagXoso66 = false;
    private boolean flagReset = false;
    private LocalTime timeNext = null;
    private String xoso66_data = "";
    private String issue = "";
    private String cau = "";
    private String bet = "";
    private int countBet = 0;
    private int countBetReal = 0;
    private int countWin = 0;
    private boolean flagBet = false;
    private int countId = 0;
    private int kpi = 50000;
    private int kpiTong = 350000;
    private final Integer[] betLevel = {12500, 25000, 50000, 100000, 200000};
    private final Integer[] betLevelReal = {12500, 25000, 50000, 100000, 200000};
    private String arrCau[] = {
        "TTTNT",
        "NNNTT",
        "NNNTN",
        "TNNNT",
        "TTTNN",
        "TNTTT",
        "NTTTN",
        "NTNNN"
    };

    public AutoVip() {
        initComponents();
        setTitle("AutoVip");
        setResizable(false);
        setLocationRelativeTo(null);
        ImageIcon logoIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\com\\autovip\\logo.png");
        setIconImage(logoIcon.getImage());

        xoso66_setData.setLineWrap(true);
        xoso66_setData.setWrapStyleWord(true);
        xoso66_setData.disable();

        // Tao file database tong lai
        try {
            File dbAuto = new File(this.filePath);
            if (!dbAuto.exists()) {
                dbAuto.mkdir();
            }

            fileTongLaiReal = new File(dbAuto, "tong_lai_real.txt");
            fileTongLai = new File(dbAuto, "tong_lai.txt");
            fileCountBetVip = new File(dbAuto, "countBetVip.txt");
            fileCountBetPro = new File(dbAuto, "countBetPro.txt");

            if (!fileTongLaiReal.exists()) {
                fileTongLaiReal.createNewFile();
            }
            if (!fileTongLai.exists()) {
                fileTongLai.createNewFile();
            }
            if (!fileCountBetVip.exists()) {
                fileCountBetVip.createNewFile();
            }
            if (!fileCountBetPro.exists()) {
                fileCountBetPro.createNewFile();
            }
        } catch (Exception e) {
        }
    }

    private void xoso66_login(WebDriver driver) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.cssSelector(".x-btn")));
//            List<WebElement> els = driver.findElements(By.cssSelector(".el-input__inner"));
//            WebElement tk = els.get(0);
//            tk.sendKeys(xoso_TK.getText().trim());
//            WebElement mk = els.get(1);
//            mk.sendKeys(xoso_MK.getText().trim());
//            driver.findElement(By.cssSelector(".el-button.btn-login.el-button--default")).click();
//            Thread.sleep(3000);
            driver.findElement(By.cssSelector(".el-button.btn-guestLogin.el-button--default")).click();
            Thread.sleep(3000);

            // tat thong bao
            WebElement wapper = driver.findElement(By.cssSelector(".el-dialog__wrapper.announce-dialog"));
            wapper.findElement(By.cssSelector(".el-dialog__headerbtn")).click();
            Thread.sleep(3000);

            cauTX.setText("Cầu: " + rdCauTX());
            lanDatTiepTheo.setText("Lần đặt tiếp theo: " + (countBet + 1));
            soTienDat.setText("Số tiền: " + numb.format(betLevel[countBet]));
            Database.fileWriter(countBet, fileCountBetVip);
            countId = 0;
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
                driver.get(urlWeb + "lottery?tabName=Lottery&id=67");
                driver.navigate().refresh();
                Thread.sleep(2000);
                xoso66_login(driver);
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
                    // lay phan tu dau tien
                    Map<String, String> map = xoso66_getData();
                    String id = "";
                    for (String key : map.keySet()) {
                        if (key.matches("\\d+")) {
                            id = key;
                            break;
                        }
                    }

                    if (!issue.equals(id)) {
                        countId = 0;
                        issue = id;
                        xoso66_data += (map.get(issue) == null) ? "" : map.get(issue);
                        xoso66_data = processData(xoso66_data, 18);
                        xoso66_id.setText(issue);
                        xoso66_setData.setText(xoso66_data);

                        // ket qua cau 3 - 4 - 5
                        List<String> results = findClosestPatterns(xoso66_data);
                        if (!results.get(0).equals("-")) {
                            xoso66_cau3.setText(results.get(0));
                        }
                        if (!results.get(1).equals("-")) {
                            xoso66_cau4.setText(results.get(1));
                        }
                        if (!results.get(2).equals("-")) {
                            xoso66_cau5.setText(results.get(2));
                        }

                        // Kiem tra thang hoac thua neu co dat cuoc
                        if (!bet.isEmpty()) {
                            if (map.get("B") == null) {
                                if (bet.equals(map.get(issue))) { // thang
                                    // luu vao database tong_lai_real
                                    if (countBet == betLevel.length - 5) {
                                        int tongLaiReal = Database.fileReader(fileTongLaiReal);
                                        tongLaiReal += betLevelReal[0];
                                        Database.fileWriter(tongLaiReal, fileTongLaiReal);
                                        labelTongLai.setText(numb.format(tongLaiReal));
                                        countBetReal = 0;
                                    }

                                    // luu vao database tong_lai
                                    int tongLai = Database.fileReader(fileTongLai);
                                    tongLai += betLevel[0];
                                    Database.fileWriter(tongLai, fileTongLai);

                                    // tien lai~ tam thoi
                                    tamLai += betLevel[0];
                                    labelTamLai.setText(numb.format(tamLai));

                                    countBet = 0;
                                    cauTX.setText("Cầu: " + rdCauTX());
                                    lanDatTiepTheo.setText("Lần đặt tiếp theo: " + (countBet + 1));
                                    soTienDat.setText("Số tiền: " + numb.format(betLevel[countBet]));
                                    Database.fileWriter(countBet, fileCountBetVip);

                                    // tat thong bao win
                                    try {
                                        WebElement notice = driver.findElement(By.cssSelector(".winning-notice-wp"));
                                        if (notice.isDisplayed() && notice.isEnabled()) {
                                            notice.findElement(By.cssSelector(".iconfont.icon-icon_close_white")).click();
                                        }
                                    } catch (Exception e) {
                                    }
                                } else { // thua
                                    // Real
                                    if (countBet == betLevel.length - 5) {
                                        countBetReal += 1;
                                        if (countBetReal > betLevelReal.length) {
                                            int tongLaiReal = Database.fileReader(fileTongLaiReal);
                                            for (int i = 0; i < betLevelReal.length; i++) {
                                                tongLaiReal -= betLevelReal[i];
                                            }
                                            Database.fileWriter(tongLaiReal, fileTongLaiReal);
                                            labelTongLai.setText(numb.format(tongLaiReal));
                                            countBetReal = 0;
                                        }
                                    }

                                    countBet += 1;
                                    if (countBet < betLevel.length) { // hoa`
                                        lanDatTiepTheo.setText("Lần đặt tiếp theo: " + (countBet + 1));
                                        soTienDat.setText("Số tiền: " + numb.format(betLevel[countBet]));
                                        Database.fileWriter(countBet, fileCountBetVip);
                                    } else { // thua het
                                        // luu vao database tong_lai
                                        int tongLai = Database.fileReader(fileTongLai);
                                        for (int i = 0; i < betLevel.length; i++) {
                                            tongLai -= betLevel[i];
                                        }
                                        Database.fileWriter(tongLai, fileTongLai);

                                        shuffleArray(arrCau);
                                        countBet = 0;
                                        cau = "";
                                        cauTX.setText("Cầu: " + rdCauTX());
                                        lanDatTiepTheo.setText("Lần đặt tiếp theo: " + (countBet + 1));
                                        soTienDat.setText("Số tiền: " + numb.format(betLevel[countBet]));
                                        Database.fileWriter(countBet, fileCountBetVip);
                                    }
                                }
                            } else { // thua do bao~
                                // Real
                                if (countBet == betLevel.length - 5) {
                                    countBetReal += 1;
                                    if (countBetReal > betLevelReal.length) {
                                        int tongLaiReal = Database.fileReader(fileTongLaiReal);
                                        for (int i = 0; i < betLevelReal.length; i++) {
                                            tongLaiReal -= betLevelReal[i];
                                        }
                                        Database.fileWriter(tongLaiReal, fileTongLaiReal);
                                        labelTongLai.setText(numb.format(tongLaiReal));
                                        countBetReal = 0;
                                    }
                                }

                                countBet += 1;
                                if (countBet < betLevel.length) { // hoa`
                                    lanDatTiepTheo.setText("Lần đặt tiếp theo: " + (countBet + 1));
                                    soTienDat.setText("Số tiền: " + numb.format(betLevel[countBet]));
                                    Database.fileWriter(countBet, fileCountBetVip);
                                } else { // thua het
                                    // luu vao database tong_lai
                                    int tongLai = Database.fileReader(fileTongLai);
                                    for (int i = 0; i < betLevel.length; i++) {
                                        tongLai -= betLevel[i];
                                    }
                                    Database.fileWriter(tongLai, fileTongLai);

                                    shuffleArray(arrCau);
                                    countBet = 0;
                                    cau = "";
                                    cauTX.setText("Cầu: " + rdCauTX());
                                    lanDatTiepTheo.setText("Lần đặt tiếp theo: " + (countBet + 1));
                                    soTienDat.setText("Số tiền: " + numb.format(betLevel[countBet]));
                                    Database.fileWriter(countBet, fileCountBetVip);
                                }
                            }

                            datCuoc.setText("Đặt cược: -");
                            bet = "";
                        }

                        // doi mau red hoac green khi thay doi dong` tien`
                        int tongLaiReal = Database.fileReader(fileTongLaiReal);
                        labelTongLai.setText(numb.format(tongLaiReal));
                        if (tongLaiReal < 0) {
                            labelTongLai.setForeground(color_red);
                        } else {
                            labelTongLai.setForeground(color_green);
                        }

                        // Neu du kpi va 2 countBet = 0 thi ngung`
                        xoso66_stopToWin();

                        // Dat cuoc tiep theo sau khi ket thuc thoi gian cho`
                        xoso66_timeNextBet();
                    } else {
                        // error: neu rot mang ko chay duoc thi reload lai trang
                        countId += 1;
                        if (countId > 5) {
                            driver.navigate().refresh();
                            Thread.sleep(3000);
                            countId = 0;
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.cssSelector(".x-btn")));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Kiem tra neu bi logout thi login lai
                checkLogin();
            }, 0, 8, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void xoso66_timeNextBet() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH'h'mm'p'");
        LocalTime now = LocalTime.now();

        // Dat thoi gian cho` neu du kpi
        if (tamLai >= kpi && countBet == 0 && countBetReal == 0) {
            int rdTimeNext = new Random().nextInt(30) + 10;
            timeNext = now.plusMinutes(rdTimeNext);

            tamLai = 0;
            labelTamLai.setText("0đ");
            cauTX.setText("Cầu: -");
            lanDatTiepTheo.setText("Lần đặt tiếp theo: " + (countBet + 1));
            soTienDat.setText("Số tiền: " + numb.format(betLevel[countBet]));
            labelTimeNext.setText(timeNext.format(formatter));
        }

        // Neu co thoi gian cho` thi ngung` dat
        if (timeNext != null && now.isAfter(timeNext)) {
            timeNext = null;
            labelTimeNext.setText("-");
            xoso66_cau3.setText("-");
            xoso66_cau4.setText("-");
            xoso66_cau5.setText("-");
            cau = "";
            cauTX.setText("Cầu: " + rdCauTX());

            if (flagReset) {
                xoso66_data = "";
                flagReset = false;
            }
        }

        // Dat neu ko co hoac het thoi gian cho`
        if (timeNext == null && flagBet == false) {
            List<String> results = List.of(xoso66_cau3.getText(), xoso66_cau4.getText(), xoso66_cau5.getText());

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
                if (xoso66_data.endsWith(keySub) && results.contains(value)) {
                    bet = key.charAt(key.length() - 1) + "";
                    break;
                }
            }

            if (!bet.isEmpty()) {
                String cauTN = this.cau.charAt(countBet) + "";

                soTienDat.setText("Số tiền: " + numb.format(betLevel[countBet]));
                lanDatTiepTheo.setText("Lần đặt tiếp theo: " + String.valueOf(countBet + 1));

                // Dat theo cau thuan va nghich
                if (cauTN.equals("T")) {
                    datCuoc.setText("Đặt cược: " + bet);

                    if (countBet == betLevel.length - 5) {
                        xoso66_pick(bet, String.valueOf(betLevelReal[countBetReal]));
                    }
                } else {
                    bet = bet.equals("T") ? "X" : "T";
                    datCuoc.setText("Đặt cược: " + bet);

                    if (countBet == betLevel.length - 5) {
                        xoso66_pick(bet, String.valueOf(betLevelReal[countBetReal]));
                    }
                }
            }
        }
    }

    private void xoso66_pick(String pick, String price) {
        try {
            WebElement optWp = driver.findElement(By.cssSelector(".opt-wp-2"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", optWp);
            Thread.sleep(500);

            List<WebElement> opt = optWp.findElements(By.cssSelector(".opt"));
            // Chọn T hoặc X
            WebElement targetElement = pick.equals("T") ? opt.get(0) : opt.get(1);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", targetElement);
            Thread.sleep(500);

            // Nhập số tiền
            WebElement order = driver.findElement(By.cssSelector(".my-order-list"));
            WebElement inpPrice = order.findElement(By.cssSelector("input[type='tel']"));
            inpPrice.sendKeys(Keys.CONTROL + "a");
            inpPrice.sendKeys(Keys.DELETE);
            Thread.sleep(300);
            inpPrice.sendKeys(price);
            Thread.sleep(300);

            // Đặt cược
            WebElement betButton = driver.findElement(By.cssSelector(".el-button.bet-btn.el-button--primary"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", betButton);
            Thread.sleep(2000);

            // Nếu đã click đặt cược mà không được, click thêm lần nữa
            try {
                WebElement checkButton = driver.findElement(By.cssSelector(".el-button.bet-btn.el-button--primary"));
                if (checkButton.isDisplayed() && checkButton.isEnabled()) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", targetElement);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", targetElement);
                    Thread.sleep(500);
                    WebElement order1 = driver.findElement(By.cssSelector(".my-order-list"));
                    WebElement inpPrice1 = order1.findElement(By.cssSelector("input[type='tel']"));
                    inpPrice1.sendKeys(Keys.CONTROL + "a");
                    inpPrice1.sendKeys(Keys.DELETE);
                    Thread.sleep(300);
                    inpPrice1.sendKeys(price);
                    Thread.sleep(300);

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH'h'mm'p'");
        LocalTime now = LocalTime.now();
        int tongLaiReal = Database.fileReader(fileTongLaiReal);
        int tongLai = Database.fileReader(fileTongLai);
        int countBetVip = Database.fileReader(fileCountBetVip);
        int countBetPro = Database.fileReader(fileCountBetPro);
        int tong = 0;
        for (Integer money : betLevel) {
            tong += money;
        }

        if (tongLai >= kpiTong && countBetVip == 0 && countBetPro == 0 && countBetReal == 0) {
            countWin += 1;
            if (countWin > 2) {
                Database.sendEmail("W-Vip-Pro", numb.format(tongLaiReal));
                xoso66_onOff.setText("Done");
                scheduler.shutdown();
                flagXoso66 = false;
                flagBet = true;
            } else {
                timeNext = now.plusHours(1);
                tamLai = 0;
                labelTamLai.setText("0đ");
                cauTX.setText("Cầu: -");
                lanDatTiepTheo.setText("Lần đặt tiếp theo: " + (countBet + 1));
                soTienDat.setText("Số tiền: " + numb.format(betLevel[countBet]));
                labelTimeNext.setText(timeNext.format(formatter));
                flagBet = false;
                flagReset = true;
                try {
                    Thread.sleep(60000);
                    Database.fileWriter(0, fileTongLai);
                    countId = 0;
                } catch (Exception e) {
                }
            }
            labelCountWin.setText(countWin + "");
        } else if (tongLai >= kpiTong && countBetVip == 0 && countBetPro > 0 && countBetReal == 0) {
            flagBet = true;
        } else if (tongLai <= (tong * -3) && countBetVip == 0 && countBetPro == 0 && countBetReal == 0) {
            Database.sendEmail("W-Vip-Pro", numb.format(tongLaiReal));
            xoso66_onOff.setText("Done");
            scheduler.shutdown();
            flagXoso66 = false;
            flagBet = true;
        } else if (tongLai <= (tong * -3) && countBetVip == 0 && countBetPro > 0 && countBetReal == 0) {
            flagBet = true;
        } else {
            flagBet = false;
        }
    }

    private Map<String, String> xoso66_getData() {
        Map<String, String> map = new HashMap<>();
        // Tat thong bao nhan li xi
        try {
            WebElement thongbaoLixi = driver.findElement(By.cssSelector(".redEnvRainPop"));
            if (thongbaoLixi.isDisplayed() && thongbaoLixi.isEnabled()) {
                thongbaoLixi.findElement(By.cssSelector(".redEnvRainPopClose")).click();
            }
        } catch (Exception e) {
        }

        try {
            Thread.sleep(1000);
            WebElement table = driver.findElement(By.cssSelector(".right-p"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", table);
            String issue = table.findElement(By.cssSelector(".issue")).getText();
            WebElement results = table.findElement(By.cssSelector(".result"));
            List<WebElement> childElements = results.findElements(By.cssSelector("div")); // Tìm tất cả các phần tử con

            int number1 = Integer.parseInt(childElements.get(0).getAttribute("class").replaceAll("num-", ""));
            int number2 = Integer.parseInt(childElements.get(1).getAttribute("class").replaceAll("num-", ""));
            int number3 = Integer.parseInt(childElements.get(2).getAttribute("class").replaceAll("num-", ""));

            int kq = number1 + number2 + number3;
            if (kq <= 10) {
                map.put(issue, "X");
            } else {
                map.put(issue, "T");
            }

            // bao~
            if (number1 == number2 && number2 == number3) {
                map.put("B", "B");
            }
        } catch (Exception e) {
        }
        return map;
    }

    private static String processData(String data, int maxCau) {
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

    private static void addCau(LinkedList<String> cauList, String cau, int maxCau) {
        // Nếu danh sách đã đủ số lượng cầu tối đa, xóa cầu đầu tiên
        if (cauList.size() >= maxCau) {
            cauList.removeFirst();
        }
        // Thêm cầu mới vào danh sách
        cauList.add(cau);
    }

    private String rdCauTX() {
        Random random = new Random();

        while (true) {
            int rd = random.nextInt(arrCau.length) + 0;

            if (!cau.equals(arrCau[rd])) {
                cau = arrCau[rd];
                for (int i = 0; i < 2; i++) {
                    int rdIndex = random.nextInt(cau.length()) + 0;
                    char[] chars = cau.toCharArray();
                    if (chars[rdIndex] == 'T') {
                        chars[rdIndex] = 'N';
                    } else if (chars[rdIndex] == 'N') {
                        chars[rdIndex] = 'T';
                    }
                    cau = new String(chars);
                }
                break;
            } else if (arrCau.length <= 1) {
                break;
            }
        }
        return cau;
    }

    private static void shuffleArray(String[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            String temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    public static List<String> findClosestPatterns(String mainPattern) {
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        xoso_TK = new javax.swing.JTextField();
        xoso_MK = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        xoso66_onOff = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        xoso66_setData = new javax.swing.JTextArea();
        xoso66_cau3 = new javax.swing.JLabel();
        xoso66_cau4 = new javax.swing.JLabel();
        xoso66_cau5 = new javax.swing.JLabel();
        xoso66_id = new javax.swing.JLabel();
        cauTX = new javax.swing.JLabel();
        soTienDat = new javax.swing.JLabel();
        lanDatTiepTheo = new javax.swing.JLabel();
        datCuoc = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        labelTongLai = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        labelTamLai = new javax.swing.JLabel();
        btnResetMoney = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        labelTimeNext = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        labelCountWin = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("AutoVip");
        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Tài khoản");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Mật khẩu");

        xoso66_onOff.setBackground(new java.awt.Color(51, 153, 255));
        xoso66_onOff.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        xoso66_onOff.setText("Bắt đầu");
        xoso66_onOff.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                xoso66_onOffMousePressed(evt);
            }
        });

        xoso66_setData.setColumns(20);
        xoso66_setData.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        xoso66_setData.setForeground(new java.awt.Color(102, 102, 255));
        xoso66_setData.setRows(5);
        jScrollPane2.setViewportView(xoso66_setData);

        xoso66_cau3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        xoso66_cau3.setText("-");

        xoso66_cau4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        xoso66_cau4.setText("-");

        xoso66_cau5.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        xoso66_cau5.setText("-");

        xoso66_id.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        xoso66_id.setForeground(new java.awt.Color(0, 153, 255));
        xoso66_id.setText("-");

        cauTX.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cauTX.setText("Cầu: -");

        soTienDat.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        soTienDat.setText("Số tiền: -");

        lanDatTiepTheo.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lanDatTiepTheo.setText("Lần đặt tiếp theo: -");

        datCuoc.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        datCuoc.setText("Đặt cược: -");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel3.setText("Tổng lãi:");

        labelTongLai.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        labelTongLai.setForeground(new java.awt.Color(33, 152, 45));
        labelTongLai.setText("0đ");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel4.setText("Lãi tạm:");

        labelTamLai.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        labelTamLai.setForeground(new java.awt.Color(33, 152, 45));
        labelTamLai.setText("0đ");

        btnResetMoney.setBackground(new java.awt.Color(255, 153, 0));
        btnResetMoney.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnResetMoney.setForeground(new java.awt.Color(51, 51, 0));
        btnResetMoney.setText("Làm mới dữ liệu");
        btnResetMoney.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnResetMoneyMousePressed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel5.setText("Thời gian đặt tiếp theo: ");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 153, 255));
        jLabel6.setText("AuToVip");

        labelTimeNext.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        labelTimeNext.setText("-");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel7.setText("Số lần đã thắng:");

        labelCountWin.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        labelCountWin.setText("-");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelTongLai, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(xoso_TK, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(xoso_MK, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(xoso66_onOff, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(xoso66_cau3)
                                .addGap(18, 18, 18)
                                .addComponent(xoso66_cau4)
                                .addGap(18, 18, 18)
                                .addComponent(xoso66_cau5))
                            .addComponent(cauTX)
                            .addComponent(lanDatTiepTheo)
                            .addComponent(datCuoc)
                            .addComponent(soTienDat))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelCountWin))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelTimeNext))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnResetMoney)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelTamLai, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(xoso66_id)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)))
                .addContainerGap())
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
                        .addComponent(xoso_MK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(xoso_TK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(labelTongLai)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(xoso66_cau3)
                    .addComponent(xoso66_cau4)
                    .addComponent(xoso66_cau5)
                    .addComponent(jLabel4)
                    .addComponent(labelTamLai)
                    .addComponent(btnResetMoney))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cauTX)
                    .addComponent(jLabel5)
                    .addComponent(labelTimeNext))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lanDatTiepTheo)
                    .addComponent(jLabel7)
                    .addComponent(labelCountWin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(soTienDat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(datCuoc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(xoso66_id, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void xoso66_onOffMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_xoso66_onOffMousePressed
        if (flagXoso66 == false) {
            xoso66_onOff.setText("Tắt");
            flagXoso66 = true;
            SwingUtilities.invokeLater(() -> {
                btnResetMoney.setEnabled(false);
            });

            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    ChromeOptions options = new ChromeOptions();
                    // Bật chế độ ẩn danh (Incognito)
                    options.addArguments("--incognito");
                    // Tắt tiếng trình duyệt
                    options.addArguments("--mute-audio");
                    driver = new ChromeDriver(options);
                    driver.get(urlWeb + "lottery?tabName=Lottery&id=67");

                    xoso66_login(driver);
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

    private void btnResetMoneyMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnResetMoneyMousePressed
        // TODO add your handling code here:
        if (btnResetMoney.isEnabled()) {
            Database.fileWriter(0, fileTongLai);
            Database.fileWriter(0, fileTongLaiReal);
        }
    }//GEN-LAST:event_btnResetMoneyMousePressed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(AutoVip.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AutoVip.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AutoVip.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AutoVip.class
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
                new AutoVip().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnResetMoney;
    private javax.swing.JLabel cauTX;
    private javax.swing.JLabel datCuoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelCountWin;
    private javax.swing.JLabel labelTamLai;
    private javax.swing.JLabel labelTimeNext;
    private javax.swing.JLabel labelTongLai;
    private javax.swing.JLabel lanDatTiepTheo;
    private javax.swing.JLabel soTienDat;
    private javax.swing.JLabel xoso66_cau3;
    private javax.swing.JLabel xoso66_cau4;
    private javax.swing.JLabel xoso66_cau5;
    private javax.swing.JLabel xoso66_id;
    private javax.swing.JButton xoso66_onOff;
    private javax.swing.JTextArea xoso66_setData;
    private javax.swing.JTextField xoso_MK;
    private javax.swing.JTextField xoso_TK;
    // End of variables declaration//GEN-END:variables
}
