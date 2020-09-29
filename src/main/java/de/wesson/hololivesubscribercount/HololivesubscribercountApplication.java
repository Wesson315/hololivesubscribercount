package de.wesson.hololivesubscribercount;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.wesson.hololivesubscribercount.controller.SnapshotController;
import de.wesson.hololivesubscribercount.controller.TalentController;
import de.wesson.hololivesubscribercount.model.hololive.HololiveTalent;
import de.wesson.hololivesubscribercount.model.timer.CronoManager;
import de.wesson.hololivesubscribercount.model.youtube.YoutubeAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

@SpringBootApplication
@PropertySource(value = "classpath:/application.properties")
@EnableMongoRepositories
public class HololivesubscribercountApplication implements CommandLineRunner {

    @Autowired
    private TalentController talentController;

    @Autowired
    private SnapshotController snapshotController;

    @Autowired
    public Environment env;


    public static String apiKey;
    public static String mongoURL;

    public static void main(String[] args) {


        SpringApplication.run(HololivesubscribercountApplication.class, args);


    }


    @Override
    public void run(String... args) {
        YoutubeAPI api = new YoutubeAPI();
        Logger logger = LoggerFactory.getLogger(HololivesubscribercountApplication.class);

        // Fetches all thumbnails and writes them to disk, so we dont need to bother youtube all the time.
        //getAllThumbnails(api, logger);


        apiKey = env.getProperty("user.apikey");
        mongoURL = env.getProperty("user.mongoDBString");

        logger.info("Starting CronoManager");
        try {

            CronoManager.getInstance().startScheduler(this.talentController, this.snapshotController);
        } catch (Exception e) {
            logger.error("Error starting Cronomanager!", e);
            System.exit(1);
        }
        HololiveTalent defaultTalent = new HololiveTalent("UCHsx4Hqa-1ORjQTh9TYDhww", "Takanashi Kiara Ch. hololive-EN", "Takanashi Kiara", "takanashikiara", "Takanashi_Kiara");

        try {
            logger.info("Making a test talent to see if api works (Using Best girl for this test)...");
            api.updateTalent(defaultTalent);
            logger.info("Talent: " + defaultTalent);
        } catch (Exception e) {
            logger.error("API FAILED! CANNOT START", e);
            System.exit(2);
        }
        try {

            boolean forceUpdate = args != null && args.length > 0 && "--force-update".equalsIgnoreCase(args[0]);
            logger.info("Checking the the database has entries, if no, populate!");
            if (talentController.getTalentCount() < 5 || forceUpdate) {// No talents! Populate
                if (forceUpdate) {
                    logger.info("A refresh was forced! Dropping data and fetching new!");
                    talentController.deleteAll();
                }
                // Null Gen
                HololiveTalent tokinoSora = new HololiveTalent("UCp6993wxpyDPHUpavwDFqgg", "SoraCh. ときのそらチャンネル", "Tokino Sora", "tokino_sora", "Tokino_Sora");
                HololiveTalent roboco = new HololiveTalent("UCDqI2jOz0weumE8s7paEk6g", "Roboco Ch. - ロボ子", "Roboco", "robocosan", "roboco");
                HololiveTalent sakuramiko = new HololiveTalent("UC-hM6YJuNYVAmUWxeIr9FeA", "Miko Ch. さくらみこ", "Sakura Miko", "sakuramiko35", "Sakura_Miko");
                HololiveTalent hoshimachiSuisei = new HololiveTalent("UC5CwaMl1eIgY8h02uZw7u8A", "Suisei Channel", "Hoshimachi Suisei", "suisei_hosimati", "Hoshimachi_Suisei");
                // First Gen
                HololiveTalent akaihaato = new HololiveTalent("UC1CfXB_kRs3C-zaeTG3oGyg", "Haato Channel 赤井はあと", "Akai Haato", "akaihaato", "Akai_Haato");
                HololiveTalent yozoraMel = new HololiveTalent("UCD8HOxPs4Xvsm8H0ZxXGiBw", "Mel Channel 夜空メルチャンネル", "Yozora Mel", "yozoramel", "Yozora_Mel");
                HololiveTalent natsuiroMatsuri = new HololiveTalent("UCQ0UDLQCjY0rmuxCDE38FGg", "Matsuri Channel 夏色まつり", "Natsuiro Matsuri ", "natsuiromatsuri", "Natsuiro_Matsuri");
                HololiveTalent akiRosenthal = new HololiveTalent("UCFTLzh12_nrtzqBPsTCqenA", "アキロゼCh。Vtuber/ホロライブ所属", "Aki Rosenthal", "akirosenthal", "Aki_Rosenthal");
                HololiveTalent shirakamiFubuki = new HololiveTalent("UCdn5BQ06XqgXoAxIhbqw5Rg", "フブキCh。白上フブキ", "Shirakami Fubuki", "shirakamifubuki", "Shirakami_Fubuki");
                // Second Gen
                HololiveTalent oozoraSubaru = new HololiveTalent("UCvzGlP9oQwU--Y0r9id_jnA", "Subaru Ch. 大空スバル", "Oozora Subaru", "oozorasubaru", "Oozora_Subaru");
                HololiveTalent yuzukiChoco = new HololiveTalent("UC1suqwovbL1kzsoaZgFZLKg", "Choco Ch. 癒月ちょこ", "Yuzuki Choco", "yuzukichococh", "Yuzuki_Choco");
                HololiveTalent murasakiShion = new HololiveTalent("UCXTpFs_3PqI41qX2d9tL2Rw", "Shion Ch. 紫咲シオン", "Murasaki Shion", "murasakishionch", "Murasaki_Shion");
                HololiveTalent nakiriAyame = new HololiveTalent("UC7fk0CB07ly8oSl0aqKkqFg", "Nakiri Ayame Ch. 百鬼あやめ", "Nakiri Ayame", "nakiriayame", "Nakiri_Ayame");
                HololiveTalent minatoAqua = new HololiveTalent("UC1opHUrw8rvnsadT-iGp7Cg", "Aqua Ch. 湊あくあ", "Minato Aqua", "minatoaqua", "Minato_Aqua");

                //Holo Gamers
                HololiveTalent ookamiMio = new HololiveTalent("UCp-5t9SrOQwXMU7iIjQfARg", "Mio Channel 大神ミオ", "Ookami Mio", "ookamimio", "Ookami_Mio");
                HololiveTalent nekomataOkayu = new HololiveTalent("UCvaTdHTWBGv3MKj3KVqJVCw", "Okayu Ch. 猫又おかゆ", "Nekomata Okayu", "nekomataokayu", "Nekomata_Okayu");
                HololiveTalent inugamiKorone = new HololiveTalent("UChAnqc_AY5_I3Px5dig3X1Q", "Korone Ch. 戌神ころね", "Inugami Korone", "inugamikorone", "Inugami_Korone");

                // Third Gen
                HololiveTalent usadaPekora = new HololiveTalent("UC1DCedRgGHBdm81E1llLhOQ", "Pekora Ch. 兎田ぺこら", "Usada Pekora", "usadapekora", "Usada_Pekora");
                HololiveTalent urushaRushia = new HololiveTalent("UCl_gCybOJRIgOXw6Qb4qJzQ", "Rushia Ch. 潤羽るしあ", "Uruha Rushia", "uruharushia", "Uruha_Rushia");
                HololiveTalent shiranuiFlare = new HololiveTalent("UCvInZx9h3jC2JzsIzoOebWg", "Flare Ch. 不知火フレア", "Shiranui Flare", "shiranuiflare", "Shiranui_Flare");
                HololiveTalent shiroganeNoel = new HololiveTalent("UCdyqAaZDKHXg4Ahi7VENThQ", "Noel Ch. 白銀ノエル", "Shirogane Noel", "shiroganenoel", "Shirogane_Noel");
                HololiveTalent hoshouMarine = new HololiveTalent("UCCzUftO8KOVkV4wQG1vkUvg", "Marine Ch. 宝鐘マリン", "Houshou Marine", "houshoumarine", "Houshou_Marine");

                // Fourth Gen
                HololiveTalent tsunomakiWatame = new HololiveTalent("UCqm3BQLlJfvkTsX_hvm0UmA", "Watame Ch. 角巻わため", "Tsunomaki Watame", "tsunomakiwatame", "Tsunomaki_Watame");
                HololiveTalent tokoyamiTowa = new HololiveTalent("UC1uv2Oq6kNxgATlCiez59hw", "Towa Ch. 常闇トワ", "Tokoyami Towa", "tokoyamitowa", "Tokoyami_Towa");
                HololiveTalent kiryuCoco = new HololiveTalent("UCS9uQI-jC3DE0L4IpXyvr6w", "Coco Ch. 桐生ココ", "Kiryu Coco", "kiryucoco", "Kiryu_Coco");
                HololiveTalent amaneKanata = new HololiveTalent("UCZlDXzGoo7d44bwdNObFacg", "Kanata Ch. Kanata Amane", "Amane Kanata", "amanekanatach", "Amane_Kanata");
                HololiveTalent himemoriLuna = new HololiveTalent("UCa9Y57gfeY0Zro_noHRVrnw", "Luna Ch. 姫森ルーナ", "Himemori Luna", "himemoriluna", "Himemori_Luna");

                // Fith Gen
                HololiveTalent yukihanaLamy = new HololiveTalent("UCFKOVgVbGmX65RxO3EtH3iw", "Lamy Ch. 雪花ラミィ", "Yukihana Lamy", "yukihanalamy", "Yukihana_Lamy");
                HololiveTalent momozuzunene = new HololiveTalent("UCAWSyEs_Io8MtpY3m-zqILA", "Nene Ch.桃鈴ねね", "Momosuzu Nene", "momosuzunene", "Momosuzu_Nene");
                HololiveTalent manoAloe = new HololiveTalent("UCgZuwn-O7Szh9cAgHqJ6vjw", "Aloe Ch.魔乃アロエ", "Mano Aloe", "manoaloe", "Mano_Aloe");
                HololiveTalent shishiroBotan = new HololiveTalent("UCUKD-uaobj9jiqB-VXt71mA", "Botan Ch.獅白ぼたん", "Shishiro Botan", "shishirobotan", "Shishiro_Botan");
                HololiveTalent omaruPolka = new HololiveTalent("UCK9V2B22uJYu3N7eR_BT9QA", "Polka Ch. 尾丸ポルカ", "Omaru Polka", "omarupolka", "Omaru_Polka");

                // Hololive EN
                HololiveTalent ninomaeInanis = new HololiveTalent("UCMwGHR0BTZuLsmjY_NT5Pwg", "Ninomae Ina'nis Ch. hololive-EN", "Ninomae Ina'nis", "ninomaeinanis", "Ninomae_Ina'nis");
                HololiveTalent takanashiKiara = new HololiveTalent("UCHsx4Hqa-1ORjQTh9TYDhww", "Takanashi Kiara Ch. hololive-EN", "Takanashi Kiara", "takanashikiara", "Takanashi_Kiara");
                HololiveTalent watsonAmelia = new HololiveTalent("UCyl1z3jo3XHR1riLFKG5UAg", "Watson Amelia Ch. hololive-EN", "Watson Amelia", "watsonameliaEN", "Watson_Amelia");
                HololiveTalent moriCalliope = new HololiveTalent("UCL_qhgtOy0dy1Agp8vkySQg", "Mori Calliope Ch. hololive-EN", "Mori Calliope", "moricalliope", "Mori_Calliope");
                HololiveTalent gawrGura = new HololiveTalent("UCoSrY_IQQVpmIRZ9Xf-y93g", "Gawr Gura Ch. hololive-EN", "Gawr Gura", "gawrgura", "Gawr_Gura");

                // Inonaka Music
                HololiveTalent azki = new HololiveTalent("UC0TXe_LYZ4scaW2XMyi5_kw", "AZKi Channel", "Virtual Diva AZKi", "AZKi_VDiVA", "AZKi");

                // Hololive CH G1
                HololiveTalent yogiri = new HololiveTalent("427061218", "夜霧Yogiri", "Yogiri", "yogiri_hololive", "Yogiri");
                HololiveTalent civia = new HololiveTalent("UCgNVXGlZIFK96XdEY20sVjg", "Civia Ch.", "Civia", "Civia_Hololive", "Civia");
                HololiveTalent spadeEcho = new HololiveTalent("456368455", "黑桃影", "Spade Echo", "SpadeEcho", "Spade_Echo");

                // Hololive CH G2
                HololiveTalent doris = new HololiveTalent("511613156", "朵莉丝Doris", "Doris", "Doris_Hololive", "Doris");
                HololiveTalent artia = new HololiveTalent("511613155", "阿媂娅Artia", "Artia", "Rosalyn_holoCN", "Rosalyn");
                HololiveTalent rosalyn = new HololiveTalent("511613157", "罗莎琳Rosalyn", "Rsalyn", "Artia_Hololive", "Artia");

                // Hololive IN
                HololiveTalent ayundaRisu = new HololiveTalent("UCOyYb1c43VlX9rc_lT6NKQw", "Ayunda Risu Ch. hololive-ID", "Ayunda Risu", "ayunda_risu", "Ayunda_Risu");
                HololiveTalent moonaHoshinova = new HololiveTalent("UCP0BspO_AMEe3aQqqpo89Dg", "Moona Hoshinova hololive-ID", "Moona Hoshinova", "moonahoshinova", "Moona_Hoshinova");
                HololiveTalent airaniIofifteen = new HololiveTalent("UCAoy6rzhSf4ydcYjJw3WoVg", "Airani Iofifteen Channel hololive-ID", "Airani Iofifteen", "airaniiofifteen", "Airani_Iofifteen");

                // Holostars
                HololiveTalent hanasakiMiyabi = new HololiveTalent("UC6t3-_N8A6ME1JShZHHqOMw", "Miyabi Ch. 花咲みやび", "Hanasaki Miyabi", "miyabihanasaki", "Hanasaki_Miyabi");
                HololiveTalent kagamikirach = new HololiveTalent("UCEzsociuFqVwgZuMaZqaCsg", "Kira Ch. 鏡見キラ", "Kagami Kira", "kagamikirach", "Kagami_Kira");
                HololiveTalent kanadeIzuru = new HololiveTalent("UCZgOv3YDEs-ZnZWDYVwJdmA", "Izuru Ch. 奏手イヅル", "Kanade Izuru", "kanadeizuru", "Kanade_Izuru");
                HololiveTalent arurandeisu = new HololiveTalent("UCKeAhJvy8zgXWbh9duVjIaQ", "Aruran Ch. アルランディス", "Arurandeisu", "Artia_Hololive", "Arurandeisu");
                HololiveTalent rikka = new HololiveTalent("UC9mf_ZVpouoILRY9NUIaK-w", "Rikka ch.律可", "Rikka", "rikkaroid", "Rikka");
                HololiveTalent astel = new HololiveTalent("UCNVEsYbiZjH5QLmGeSgTSzg", "astel ch.アステル", "Astel Leda", "astelleda", "Astel_Leda");
                HololiveTalent temma = new HololiveTalent("UCGNI4MENvnsymYjKiZwv9eg", "Temma Ch. 岸堂天真", "Kishido Temma", "kishidotemma", "Kishido_Temma");
                HololiveTalent roberu = new HololiveTalent("UCANDOlYTJT7N5jlRC3zfzVA", "Roberu Ch. 夕刻ロベル", "Yukoku Roberu", "yukokuroberu", "Yukoku_Roberu");
                HololiveTalent shien = new HololiveTalent("UChSvpZYRPh0FvG4SJGSga3g", "Shien Ch.影山シエン", "Kageyama Shien", "kageyamashien", "Kageyama_Shien");
                HololiveTalent oga = new HololiveTalent("UCwL7dgTxKo8Y4RFIKWaf8gA", "Oga Ch.荒咬オウガ", "Aragami Oga", "aragamioga", "Aragami_Oga");


                logger.info("Created all Talents! Fetching Subscribers and Adding to Database");

                for (HololiveTalent talent : HololiveTalent.talents) {
                    api.updateTalent(talent);
                    logger.info(talent.toString());
                    talentController.addOrUpdateTalent(talent);
                }
            } else {
                logger.info("Database has entires! No need to refetch.");
            }
        } catch (Exception e) {
            logger.error("Error while creating and populating talents!", e);
            CronoManager.getInstance().stopScheduler();
            System.exit(3);
        }

        logger.info("Done! Listening for requests.");

       // snapshotController.takeSnapshot(defaultTalent);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
       /* logger.info("-------> ALL TALENTS");
        try {
            File f = new File("data/talents.json");
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            writer.write(gson.toJson(talentController.getAllTalents()));
            writer.flush();
            writer.close();
            logger.info("-------> ALL HISTORY ENTRIES");
            f = new File("data/entries.json");
            writer = new BufferedWriter(new FileWriter(f));
            writer.write(gson.toJson(repository.findAll()));
            writer.flush();
            writer.close();

            System.out.println("DONE");

        } catch (Exception e) {
            e.printStackTrace();
        }
*/

    }


    private void getAllThumbnails(YoutubeAPI api, Logger logger) {
        try {
            for (HololiveTalent talent : talentController.getAllTalents()) {
                logger.info("Getting avatar for talent " + talent.getIdolName());
                logger.info("Thumbnail URL: " + talent.getThumbnailID());
                URL url = new URL(talent.getThumbnailID());
                InputStream in = new BufferedInputStream(url.openStream());
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int n = 0;
                while (-1 != (n = in.read(buf))) {
                    out.write(buf, 0, n);
                }
                out.close();
                in.close();
                byte[] response = out.toByteArray();
                String thumbnailPath = api.getThumbnailPath(talent.getChannelID());
                logger.info("Saving thumbnail to file: " + thumbnailPath);
                FileOutputStream fos = new FileOutputStream(thumbnailPath);
                fos.write(response);
                fos.flush();
                fos.close();
                logger.info("Done, setting talents path to file and sleeping 100 ms to not get rate limited lmao");
                talent.setThumbnailID(thumbnailPath);
                talentController.addOrUpdateTalent(talent);
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String getApiKey() {
        return apiKey;
    }

    public static String getMongoURL() {
        return mongoURL;
    }
}
