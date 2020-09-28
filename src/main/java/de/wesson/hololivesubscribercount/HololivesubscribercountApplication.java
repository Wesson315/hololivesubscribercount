package de.wesson.hololivesubscribercount;

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

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

@SpringBootApplication
@PropertySource(value = "classpath:/application.properties")
public class HololivesubscribercountApplication implements CommandLineRunner {

    @Autowired
    private TalentController talentController;

    @Autowired
    private SnapshotController snapshotController;

    @Autowired
    public Environment env;

    public static String apiKey;

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
        logger.info("Starting CronoManager");
        try {

                CronoManager.getInstance().startScheduler(this.talentController, this.snapshotController);
        } catch (Exception e) {
            logger.error("Error starting Cronomanager!", e);
            System.exit(1);
        }

        try {
            logger.info("Making a test talent to see if api works (Using Best girl for this test)...");
            HololiveTalent takanashiKiara = new HololiveTalent("UCHsx4Hqa-1ORjQTh9TYDhww", "Takanashi Kiara Ch. hololive-EN", "Takanashi Kiara");
            api.updateTalent(takanashiKiara);
            logger.info("Talent: " + takanashiKiara);
        } catch (Exception e) {
            logger.error("API FAILED! CANNOT START", e);
            System.exit(2);
        }
        try {

            boolean forceUpdate = args != null && args.length >0 &&  "--force-update".equalsIgnoreCase(args[0]);
            logger.info("Checking the the database has entries, if no, populate!");
            if (talentController.getTalentCount() < 5 || forceUpdate) {// No talents! Populate
                if(forceUpdate){
                    logger.info("A refresh was forced! Dropping data and fetching new!");
                    talentController.deleteAll();
                }
                // Null Gen
                HololiveTalent tokinoSora = new HololiveTalent("UCp6993wxpyDPHUpavwDFqgg", "SoraCh. ときのそらチャンネル", "Tokino Sora");
                HololiveTalent roboco = new HololiveTalent("UCDqI2jOz0weumE8s7paEk6g", "Roboco Ch. - ロボ子", "Roboco");
                HololiveTalent sakuramiko = new HololiveTalent("UC-hM6YJuNYVAmUWxeIr9FeA", "Miko Ch. さくらみこ", "Sakura Miko");
                HololiveTalent hoshimachiSuisei = new HololiveTalent("UC5CwaMl1eIgY8h02uZw7u8A", "Suisei Channel", "Hoshimachi Suisei");
                // First Gen
                HololiveTalent akaihaato = new HololiveTalent("UC1CfXB_kRs3C-zaeTG3oGyg", "Haato Channel 赤井はあと", "Akai Haato");
                HololiveTalent yozoraMel = new HololiveTalent("UCD8HOxPs4Xvsm8H0ZxXGiBw", "Mel Channel 夜空メルチャンネル", "Yozora Mel");
                HololiveTalent natsuiroMatsuri = new HololiveTalent("UCQ0UDLQCjY0rmuxCDE38FGg", "Matsuri Channel 夏色まつり", "Natsuiro Matsuri ");
                HololiveTalent akiRosenthal = new HololiveTalent("UCFTLzh12_nrtzqBPsTCqenA", "アキロゼCh。Vtuber/ホロライブ所属", "Aki Rosenthal");
                HololiveTalent shirakamiFubuki = new HololiveTalent("UCdn5BQ06XqgXoAxIhbqw5Rg", "フブキCh。白上フブキ", "Shirakami Fubuki");
                // Second Gen
                HololiveTalent oozoraSubaru = new HololiveTalent("UCvzGlP9oQwU--Y0r9id_jnA", "Subaru Ch. 大空スバル", "Oozora Subaru");
                HololiveTalent yuzukiChoco = new HololiveTalent("UC1suqwovbL1kzsoaZgFZLKg", "Choco Ch. 癒月ちょこ", "Yuzuki Choco");
                HololiveTalent murasakiShion = new HololiveTalent("UCXTpFs_3PqI41qX2d9tL2Rw", "Shion Ch. 紫咲シオン", "Murasaki Shion");
                HololiveTalent nakiriAyame = new HololiveTalent("UC7fk0CB07ly8oSl0aqKkqFg", "Nakiri Ayame Ch. 百鬼あやめ", "Nakiri Ayame");
                HololiveTalent minatoAqua = new HololiveTalent("UC1opHUrw8rvnsadT-iGp7Cg", "Aqua Ch. 湊あくあ", "Minato Aqua");

                //Holo Gamers
                HololiveTalent ookamiMio = new HololiveTalent("UCp-5t9SrOQwXMU7iIjQfARg", "Mio Channel 大神ミオ", "Ookami Mio");
                HololiveTalent nekomataOkayu = new HololiveTalent("UCvaTdHTWBGv3MKj3KVqJVCw", "Okayu Ch. 猫又おかゆ", "Nekomata Okayu");
                HololiveTalent inugamiKorone = new HololiveTalent("UChAnqc_AY5_I3Px5dig3X1Q", "Korone Ch. 戌神ころね", "Inugami Korone");

                // Third Gen
                HololiveTalent usadaPekora = new HololiveTalent("UC1DCedRgGHBdm81E1llLhOQ", "Pekora Ch. 兎田ぺこら", "Usada Pekora");
                HololiveTalent urushaRushia = new HololiveTalent("UCl_gCybOJRIgOXw6Qb4qJzQ", "Rushia Ch. 潤羽るしあ", "Uruha Rushia");
                HololiveTalent shiranuiFlare = new HololiveTalent("UCvInZx9h3jC2JzsIzoOebWg", "Flare Ch. 不知火フレア", "Shiranui Flare");
                HololiveTalent shiroganeNoel = new HololiveTalent("UCdyqAaZDKHXg4Ahi7VENThQ", "Noel Ch. 白銀ノエル", "Shirogane Noel");
                HololiveTalent hoshouMarine = new HololiveTalent("UCCzUftO8KOVkV4wQG1vkUvg", "Marine Ch. 宝鐘マリン", "Houshou Marine");

                // Fourth Gen
                HololiveTalent tsunomakiWatame = new HololiveTalent("UCqm3BQLlJfvkTsX_hvm0UmA", "Watame Ch. 角巻わため", "Tsunomaki Watame");
                HololiveTalent tokoyamiTowa = new HololiveTalent("UC1uv2Oq6kNxgATlCiez59hw", "Towa Ch. 常闇トワ", "Tokoyami Towa");
                HololiveTalent kiryuCoco = new HololiveTalent("UCS9uQI-jC3DE0L4IpXyvr6w", "Coco Ch. 桐生ココ", "Kiryu Coco");
                HololiveTalent amaneKanata = new HololiveTalent("UCZlDXzGoo7d44bwdNObFacg", "Kanata Ch. Kanata Amane", "Amane Kanata");
                HololiveTalent himemoriLuna = new HololiveTalent("UCa9Y57gfeY0Zro_noHRVrnw", "Luna Ch. 姫森ルーナ", "Himemori Luna");

                // Fith Gen
                HololiveTalent yukihanaLamy = new HololiveTalent("UCFKOVgVbGmX65RxO3EtH3iw", "Lamy Ch. 雪花ラミィ", "Yukihana Lamy");
                HololiveTalent momozuzunene = new HololiveTalent("UCAWSyEs_Io8MtpY3m-zqILA", "Nene Ch.桃鈴ねね", "Momosuzu Nene");
                HololiveTalent manoAloe = new HololiveTalent("UCgZuwn-O7Szh9cAgHqJ6vjw", "Aloe Ch.魔乃アロエ", "Mano Aloe");
                HololiveTalent shishiroBotan = new HololiveTalent("UCUKD-uaobj9jiqB-VXt71mA", "Botan Ch.獅白ぼたん", "Shishiro Botan");
                HololiveTalent omaruPolka = new HololiveTalent("UCK9V2B22uJYu3N7eR_BT9QA", "Polka Ch. 尾丸ポルカ", "Omaru Polka");

                // Hololive EN
                HololiveTalent ninomaeInanis = new HololiveTalent("UCMwGHR0BTZuLsmjY_NT5Pwg", "Ninomae Ina'nis Ch. hololive-EN", "Ninomae Ina'nis");
                HololiveTalent takanashiKiara = new HololiveTalent("UCHsx4Hqa-1ORjQTh9TYDhww", "Takanashi Kiara Ch. hololive-EN", "Takanashi Kiara");
                HololiveTalent watsonAmelia = new HololiveTalent("UCyl1z3jo3XHR1riLFKG5UAg", "Watson Amelia Ch. hololive-EN", "Watson Amelia");
                HololiveTalent moriCalliope = new HololiveTalent("UCL_qhgtOy0dy1Agp8vkySQg", "Mori Calliope Ch. hololive-EN", "Mori Calliope");
                HololiveTalent gawrGura = new HololiveTalent("UCoSrY_IQQVpmIRZ9Xf-y93g", "Gawr Gura Ch. hololive-EN", "Gawr Gura");

                // Inonaka Music
                HololiveTalent azki = new HololiveTalent("UC0TXe_LYZ4scaW2XMyi5_kw", "AZKi Channel", "Virtual Diva AZKi");

                // Hololive CH G1
                HololiveTalent yogiri = new HololiveTalent("427061218", "夜霧Yogiri", "Yogiri");
                HololiveTalent civia = new HololiveTalent("UCgNVXGlZIFK96XdEY20sVjg", "Civia Ch.", "Civia");
                HololiveTalent spadeEcho = new HololiveTalent("456368455", "黑桃影", "Spade Echo");

                // Hololive CH G2
                HololiveTalent doris = new HololiveTalent("511613156", "朵莉丝Doris", "Doris");
                HololiveTalent artia = new HololiveTalent("511613155", "阿媂娅Artia", "Artia");
                HololiveTalent rosalyn = new HololiveTalent("511613157", "罗莎琳Rosalyn", "Rsalyn");

                // Hololive IN
                HololiveTalent ayundaRisu = new HololiveTalent("UCOyYb1c43VlX9rc_lT6NKQw", "Ayunda Risu Ch. hololive-ID", "Ayunda Risu");
                HololiveTalent moonaHoshinova = new HololiveTalent("UCP0BspO_AMEe3aQqqpo89Dg", "Moona Hoshinova hololive-ID", "Moona Hoshinova");
                HololiveTalent airaniIofifteen = new HololiveTalent("UCAoy6rzhSf4ydcYjJw3WoVg", "Airani Iofifteen Channel hololive-ID", "Airani Iofifteen");

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
                while (-1!=(n=in.read(buf)))
                {
                    out.write(buf, 0, n);
                }
                out.close();
                in.close();
                byte[] response = out.toByteArray();
                String thumbnailPath = api.getThumbnailPath(talent.getChannelID());
                logger.info("Saving thumbnail to file: " +thumbnailPath);
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

}
