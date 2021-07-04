
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author yek
 */
public class Main {

    public static void main(String[] args) {
        List<Doviz> list = kurlar();

        list.forEach((doviz) -> {
            System.out.println(doviz.toString());
        });
    }

    static List<Doviz> kurlar() {
        List<Doviz> list = new ArrayList<>();

        try {
            String url = "https://www.bloomberght.com/doviz";

            Document document = Jsoup.connect(url).timeout(5000).get();

            Elements elements = document.select("div.marketsData");

            for (Element element : elements.select("tr")) {
                String dovizCins = element.select("a").attr("title");

                if (!dovizCins.equals("")) {
                    String data = element.select("td").text();

                    data = data.replace(dovizCins, "");
                    data = data.trim();
                    data = data.replaceAll(",", ".");

                    String[] items = data.split("[\\s+]");

                    list.add(new Doviz(dovizCins, items));
                }
            }

        } catch (IOException e) {
            System.err.println("HATA : " + e);
        }
        return list;
    }
}

class Doviz {

    LocalDate tarih = LocalDate.now();
    String doviz;
    String alis;
    String satis;
    String degisim;
    String saat;

    public Doviz(String doviz, String[] items) {
        this.doviz = doviz;
        this.alis = items[0];
        this.satis = items[1];
        this.degisim = items[2];
        this.saat = items[3];
    }

    @Override
    public String toString() {
        return "Doviz{" + "tarih=" + tarih + ", doviz=" + doviz + ", alis=" + alis + ", satis=" + satis + ", degisim=" + degisim + ", saat=" + saat + '}';
    }
}
