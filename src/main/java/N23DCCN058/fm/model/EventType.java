
package N23DCCN058.fm.model;


public enum EventType {
    GOAL("Bàn thắng"),
    OWN_GOAL("Phản lưới nhà"),
    PENALTY_GOAL("Bàn thắng phạt đền"),
    YELLOW_CARD("Thẻ vàng"),
    RED_CARD("Thẻ đỏ"),
    SUBSTITUTION_OUT("Ra sân"),
    SUBSTITUTION_IN("Vào sân"),
    FOUL("Phạm lỗi"),
    MATCH_START("Bắt đầu trận đấu"),
    MATCH_END("Kết thúc trận đấu");
    
    private final String vietnamese;

    // Constructor
    EventType(String vietnamese) {
        this.vietnamese = vietnamese;
    }
    
    public String getVietnamese() {
        return vietnamese;
    } 
    
    public static EventType fromString(String value){
        if(value == null) return null;
        return EventType.valueOf(value.toUpperCase());
    }
    
    public static EventType fromVietnamese(String vietnamese) {
        if (vietnamese == null || vietnamese.isBlank()) return null;

        for (EventType type : EventType.values()) {
            if (type.getVietnamese().equalsIgnoreCase(vietnamese.trim())) {
                return type;
            }
        }
        throw new IllegalArgumentException("Không tìm thấy EventType tương ứng với: " + vietnamese);
    }

    @Override
    public String toString() {
        return "EventType{" + "ordinal=" + ordinal() + ", name=" + name() + '}';
    }
    
    
}
