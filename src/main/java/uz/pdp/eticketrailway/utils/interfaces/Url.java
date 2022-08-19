package uz.pdp.eticketrailway.utils.interfaces;



public interface Url {
    String TOKEN="bot"+ Security.TOKEN +"/";
    String TELEGRAM_BASE="https://api.telegram.org/";
    String SUPPORT_BOT="https://t.me/test_support01_bot";

    String BASE_WEBHOOK="api/telegram";
    String URL_USER="api/user";

    String FULL_REQUEST=TELEGRAM_BASE+TOKEN;



    String GLOBAL="https://localhost:8082/";





    String RAILWAY_BASE="https://e-ticket.railway.uz/api/v2/train/";
    String RAILWAY_STATION="availability/space/between/stations";
    String RAILWAY_PLACE="given/availability/place";


}
