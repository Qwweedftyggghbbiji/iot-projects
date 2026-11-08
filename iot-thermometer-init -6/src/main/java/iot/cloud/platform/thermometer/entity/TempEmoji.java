package iot.cloud.platform.thermometer.entity;

public class TempEmoji {
    private Integer id;
    private Integer mint;
    private Integer maxt;
    private String emojiName;

    // getter 和 setter 方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMint() {
        return mint;
    }

    public void setMint(Integer mint) {
        this.mint = mint;
    }

    public Integer getMaxt() {
        return maxt;
    }

    public void setMaxt(Integer maxt) {
        this.maxt = maxt;
    }

    public String getEmojiName() {
        return emojiName;
    }

    public void setEmojiName(String emojiName) {
        this.emojiName = emojiName;
    }
}