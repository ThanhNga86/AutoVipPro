/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Lenovo
 */
public class Test {

    private int tongNam = 0;
    private int tongThang = 0;
    private int tongLai = 0;
    private int countStop = 0;
    private int countData = 1920;
    private String xoso66_1_cau3 = "-";
    private String xoso66_1_cau4 = "-";
    private String xoso66_1_cau5 = "-";
    private String xoso66_2_cau3 = "-";
    private String xoso66_2_cau4 = "-";
    private String xoso66_2_cau5 = "-";
    private String xoso66_1_data = "";
    private String xoso66_2_data = "";
    private String cau_1 = "";
    private String cau_2 = "";
    private String bet_1 = "";
    private String bet_2 = "";
    private int countBet_1 = 0;
    private int countBet_2 = 0;
    private int countBetReal_1 = 0;
    private int countBetReal_2 = 0;
    private boolean flagBet_1 = false;
    private boolean flagBet_2 = false;
    private int countWait_1 = 0;
    private int countWait_2 = 0;
    private Integer[] betLevel_1 = {12500, 25000, 50000, 100000, 200000};
    private Integer[] betLevel_2 = {12500, 25000, 50000, 100000, 200000};
    private Integer[] betLevelReal_1 = {10000, 20000, 40000, 80000, 160000};
    private Integer[] betLevelReal_2 = {10000, 20000, 40000, 80000, 160000};
    private String arrCau_1[] = new String[]{
        "TTTNT",
        "NNNTT",
        "NNNTN",
        "TNNNT",
        "TTTNN",
        "TNTTT",
        "NTTTN",
        "NTNNN"
    };
    private String arrCau_2[] = new String[]{
        "TTTNT",
        "NNNTT",
        "NNNTN",
        "TNNNT",
        "TTTNN",
        "TNTTT",
        "NTTTN",
        "NTNNN"
    };

    private void reset() {
        arrCau_1 = new String[]{
            "TTTNT",
            "NNNTT",
            "NNNTN",
            "TNNNT",
            "TTTNN",
            "TNTTT",
            "NTTTN",
            "NTNNN"
        };
        arrCau_2 = new String[]{
            "TTTNT",
            "NNNTT",
            "NNNTN",
            "TNNNT",
            "TTTNN",
            "TNTTT",
            "NTTTN",
            "NTNNN"
        };
        countStop = 0;
        tongLai = 0;
        xoso66_1_cau3 = "-";
        xoso66_1_cau4 = "-";
        xoso66_1_cau5 = "-";
        xoso66_2_cau3 = "-";
        xoso66_2_cau4 = "-";
        xoso66_2_cau5 = "-";
        xoso66_1_data = "";
        xoso66_2_data = "";
        cau_1 = "";
        cau_2 = "";
        bet_1 = "";
        bet_2 = "";
        countBet_1 = 0;
        countBet_2 = 0;
        countBetReal_1 = 0;
        countBetReal_2 = 0;
        flagBet_1 = false;
        flagBet_2 = false;
        countWait_1 = 0;
        countWait_2 = 0;
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

    public Test() {
        NumberFormat numb = NumberFormat.getCurrencyInstance(new Locale("Vi", "VN"));
        Random rd = new Random();
        for (int e = 0; e < 12; e++) {
            for (int i = 0; i < 30; i++) {
                reset();
                rdCauTX_1();
                rdCauTX_2();

                for (int j = 0; j < countData; j++) {
                    countStop += 1;
                    int xs1 = rd.nextInt(6) + 1;
                    int xs2 = rd.nextInt(6) + 1;
                    int xs3 = rd.nextInt(6) + 1;
                    int kq = xs1 + xs2 + xs3;

                    String kqTX;
                    if (kq <= 10) {
                        kqTX = "X";
                    } else {
                        kqTX = "T";
                    }
                    if (xs1 == xs2 && xs2 == xs3) {
                        kqTX = "B";
                    }
                    taiXiu(kqTX);

                    String kqCL;
                    if (kq % 2 == 0) {
                        kqCL = "C";
                    } else {
                        kqCL = "L";
                    }
                    if (xs1 == xs2 && xs2 == xs3) {
                        kqCL = "B";
                    }
                    chanLe(kqCL);

                    if (flagBet_1 == true && flagBet_2 == true) {
                        break;
                    }
                }
                if (countBetReal_1 > 0) {
                    for (int a = 0; a < countBetReal_1; a++) {
                        tongLai -= betLevelReal_1[a];
                    }
                    System.out.println(countBetReal_1);
                }
                if (countBetReal_2 > 0) {
                    for (int a = 0; a < countBetReal_2; a++) {
                        tongLai -= betLevelReal_2[a];
                    }
                    System.out.println(countBetReal_2);
                }
                tongThang += tongLai;
            }
            System.out.println("Thang " + (e + 1) + ": " + numb.format(tongThang));
            tongNam += tongThang;
            tongThang = 0;
        }
        System.out.println("Tong nam: " + numb.format(tongNam));
    }

    private void taiXiu(String kq) {
        countWait_1 += 1;
        xoso66_1_data += kq;
        xoso66_1_data = processData(xoso66_1_data, 18);

        List<String> results = findClosestPatterns_1(xoso66_1_data);
        if (!results.get(0).equals("-")) {
            xoso66_1_cau3 = results.get(0);
        }
        if (!results.get(1).equals("-")) {
            xoso66_1_cau4 = results.get(1);
        }
        if (!results.get(2).equals("-")) {
            xoso66_1_cau5 = results.get(2);
        }

        if (!bet_1.isEmpty()) {
            if (bet_1.equals(kq)) { // thua
                if (countBet_1 == betLevel_1.length - 5) {
                    countBetReal_1 += 1;
                    if (countBetReal_1 < betLevelReal_1.length) {
                    } else {
                        for (int i = 0; i < betLevelReal_1.length; i++) {
                            tongLai -= betLevelReal_1[i];
                        }
                        countBetReal_1 = 0;
                    }
                }

                countBet_1 = 0;
                rdCauTX_1();
            } else { // thang
                if (countBet_1 == betLevel_1.length - 5) {
                    tongLai += betLevelReal_1[0];
                    countBetReal_1 = 0;

                    int rdWait = new Random().nextInt(20) + 10;
                    countWait_1 = -rdWait;
                }

                countBet_1 += 1;
                if (countBet_1 < betLevel_1.length) {
                } else {
                    shuffleArray(arrCau_1);
                    countBet_1 = 0;
                    cau_1 = "";
                    rdCauTX_1();
                }
            }

            bet_1 = "";
        }

        xoso66_stopToWin_1();
        xoso66_nextBet_1();
    }

    private void chanLe(String kq) {
        countWait_2 += 1;
        xoso66_2_data += kq;
        xoso66_2_data = processData(xoso66_2_data, 18);

        List<String> results = findClosestPatterns_2(xoso66_2_data);
        if (!results.get(0).equals("-")) {
            xoso66_2_cau3 = results.get(0);
        }
        if (!results.get(1).equals("-")) {
            xoso66_2_cau4 = results.get(1);
        }
        if (!results.get(2).equals("-")) {
            xoso66_2_cau5 = results.get(2);
        }

        if (!bet_2.isEmpty()) {
            if (bet_2.equals(kq)) { // thua
                if (countBet_2 == betLevel_2.length - 5) {
                    countBetReal_2 += 1;
                    if (countBetReal_2 < betLevelReal_2.length) {
                    } else {
                        for (int i = 0; i < betLevelReal_2.length; i++) {
                            tongLai -= betLevelReal_2[i];
                        }
                        countBetReal_2 = 0;
                    }
                }

                countBet_2 = 0;
                rdCauTX_2();
            } else { // thang
                if (countBet_2 == betLevel_2.length - 5) {
                    tongLai += betLevelReal_2[0];
                    countBetReal_2 = 0;

                    int rdWait = new Random().nextInt(20) + 10;
                    countWait_2 = -rdWait;
                }

                countBet_2 += 1;
                if (countBet_2 < betLevel_2.length) {
                } else {
                    shuffleArray(arrCau_2);
                    countBet_2 = 0;
                    cau_2 = "";
                    rdCauTX_2();
                }
            }
            bet_2 = "";
        }

        xoso66_stopToWin_2();
        xoso66_nextBet_2();
    }

    private void xoso66_nextBet_1() {
        if (countWait_1 > 0 && flagBet_1 == false) {
            List<String> results = List.of(xoso66_1_cau3, xoso66_1_cau4, xoso66_1_cau5);

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

            if (!bet_1.isEmpty()) {
                String cauTN = this.cau_1.charAt(countBet_1) + "";

                // Dat theo cau thuan va nghich
                if (cauTN.equals("T")) {
                } else {
                    bet_1 = bet_1.equals("T") ? "X" : "T";
                }
            }
        }
    }

    private void xoso66_nextBet_2() {
        if (countWait_2 > 0 && flagBet_2 == false) {
            List<String> results = List.of(xoso66_2_cau3, xoso66_2_cau4, xoso66_2_cau5);

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

            if (!bet_2.isEmpty()) {
                String cauTN = this.cau_2.charAt(countBet_2) + "";

                // Dat theo cau thuan va nghich
                if (cauTN.equals("T")) {
                } else {
                    bet_2 = bet_2.equals("L") ? "C" : "L";
                }
            }
        }
    }

    private void xoso66_stopToWin_1() {
        int tongReal = 0;
        for (Integer money : betLevelReal_2) {
            tongReal += money;
        }

        if (tongLai <= (tongReal * -1.6) && countBetReal_1 == 0 && countBetReal_2 == 0) {
            flagBet_1 = true;
        } else if (tongLai <= (tongReal * -1.6) && countBetReal_1 == 0 && countBetReal_2 > 0) {
            flagBet_1 = true;
        } else if (tongLai <= (tongReal * -1.6) && countBetReal_1 > 0 && countBetReal_2 == 0) {
            flagBet_1 = true;
        } else if (countStop >= (countData - 120) && countBetReal_1 == 0) {
            flagBet_1 = true;
        } else {
            flagBet_1 = false;
        }
    }

    private void xoso66_stopToWin_2() {
        int tongReal = 0;
        for (Integer money : betLevelReal_2) {
            tongReal += money;
        }

        if (tongLai <= (tongReal * -1.6) && countBetReal_1 == 0 && countBetReal_2 == 0) {
            flagBet_2 = true;
        } else if (tongLai <= (tongReal * -1.6) && countBetReal_1 > 0 && countBetReal_2 == 0) {
            flagBet_2 = true;
        } else if (countStop >= (countData - 120) && countBetReal_2 == 0) {
            flagBet_2 = true;
        } else {
            flagBet_2 = false;
        }
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

    private static void shuffleArray(String[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            String temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    public static List<String> findClosestPatterns_1(String mainPattern) {
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

    public static List<String> findClosestPatterns_2(String mainPattern) {
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

    public static void main(String[] args) {
        Test t = new Test();
    }

}
