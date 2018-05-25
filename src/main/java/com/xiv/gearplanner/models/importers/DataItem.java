package com.xiv.gearplanner.models.importers;


import java.util.HashMap;
import java.util.Map;

/* Object to store the item fields temporary for conversion to an item
* from the source files */
public class DataItem {

   private String  desc;
   private Integer icon;
   private Integer itemLevel;
   private Integer equipLevel;
   private Integer slotCategoryOriginalId;
   private Integer jobUseOriginalId;
   private String  advancedMelding;
   private Boolean advMeldingAsBoolean;
   private Integer materiaSlotCount;
   private Integer jobCatOriginalId;
   private Integer originalCategoryId;
   private Integer importId;
   private String  name;
   private Double  blockRate;
   private Double  block;
   private Double  defensePhys;
   private Double  defenseMag;
   private Double  damagePhys;
   private Double  damageMag;
   private Long delay;

   private Map<Integer, Long> param = new HashMap<Integer, Long>();
   private Map<Integer, Long> paramSpecial = new HashMap<Integer, Long>();


    public DataItem() {}

    public DataItem(String desc, Integer icon, Integer itemLevel, Integer equipLevel, Integer slotCategoryOriginalId,
                    Integer jobUseOriginalId, String advancedMelding, Boolean advMeldingAsBoolean,
                    Integer materiaSlotCount, Integer jobCatOriginalId, Integer originalCategoryId,
                    Integer importId, String name, Double blockRate, Double block,
                    Double defensePhys, Double defenseMag, Double damagePhys, Double damageMag,
                    Long delay,Map<Integer, Long> param, Map<Integer, Long> paramSpecial) {

        this.desc = desc;
        this.icon = icon;
        this.itemLevel = itemLevel;
        this.equipLevel = equipLevel;
        this.slotCategoryOriginalId = slotCategoryOriginalId;
        this.jobUseOriginalId = jobUseOriginalId;
        this.advancedMelding = advancedMelding;
        this.advMeldingAsBoolean = advMeldingAsBoolean;
        this.materiaSlotCount = materiaSlotCount;
        this.jobCatOriginalId = jobCatOriginalId;
        this.originalCategoryId = originalCategoryId;
        this.importId = importId;
        this.name = name;
        this.blockRate = blockRate;
        this.block = block;
        this.defensePhys = defensePhys;
        this.defenseMag = defenseMag;
        this.damagePhys = damagePhys;
        this.damageMag = damageMag;
        this.delay = delay;
        this.param = param;
        this.paramSpecial = paramSpecial;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public Integer getItemLevel() {
        return itemLevel;
    }

    public void setItemLevel(Integer itemLevel) {
        this.itemLevel = itemLevel;
    }

    public Integer getEquipLevel() {
        return equipLevel;
    }

    public void setEquipLevel(Integer equipLevel) {
        this.equipLevel = equipLevel;
    }

    public Integer getSlotCategoryOriginalId() {
        return slotCategoryOriginalId;
    }

    public void setSlotCategoryOriginalId(Integer slotCategoryOriginalId) {
        this.slotCategoryOriginalId = slotCategoryOriginalId;
    }

    public Integer getJobUseOriginalId() {
        return jobUseOriginalId;
    }

    public void setJobUseOriginalId(Integer jobUseOriginalId) {
        this.jobUseOriginalId = jobUseOriginalId;
    }

    public String getAdvancedMelding() {
        return advancedMelding;
    }

    public void setAdvancedMelding(String advancedMelding) {
        this.advancedMelding = advancedMelding;
    }

    public Boolean getAdvMeldingAsBoolean() {
        return advMeldingAsBoolean;
    }

    public void setAdvMeldingAsBoolean(Boolean advMeldingAsBoolean) {
        this.advMeldingAsBoolean = advMeldingAsBoolean;
    }

    public Integer getMateriaSlotCount() {
        return materiaSlotCount;
    }

    public void setMateriaSlotCount(Integer materiaSlotCount) {
        this.materiaSlotCount = materiaSlotCount;
    }

    public Integer getJobCatOriginalId() {
        return jobCatOriginalId;
    }

    public void setJobCatOriginalId(Integer jobCatOriginalId) {
        this.jobCatOriginalId = jobCatOriginalId;
    }

    public Integer getOriginalCategoryId() {
        return originalCategoryId;
    }

    public void setOriginalCategoryId(Integer originalCategoryId) {
        this.originalCategoryId = originalCategoryId;
    }

    public Integer getImportId() {
        return importId;
    }

    public void setImportId(Integer importId) {
        this.importId = importId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBlockRate() {
        return blockRate;
    }

    public void setBlockRate(Double blockRate) {
        this.blockRate = blockRate;
    }

    public Double getBlock() {
        return block;
    }

    public void setBlock(Double block) {
        this.block = block;
    }

    public Double getDefensePhys() {
        return defensePhys;
    }

    public void setDefensePhys(Double defensePhys) {
        this.defensePhys = defensePhys;
    }

    public Double getDefenseMag() {
        return defenseMag;
    }

    public void setDefenseMag(Double defenseMag) {
        this.defenseMag = defenseMag;
    }

    public Double getDamagePhys() {
        return damagePhys;
    }

    public void setDamagePhys(Double damagePhys) {
        this.damagePhys = damagePhys;
    }

    public Double getDamageMag() {
        return damageMag;
    }

    public void setDamageMag(Double damageMag) {
        this.damageMag = damageMag;
    }

    public Long getDelay() {
        return delay;
    }

    public void setDelay(Long delay) {
        this.delay = delay;
    }

    public Map<Integer, Long> getParam() {
        return param;
    }

    public void setParam(Map<Integer, Long> param) {
        this.param = param;
    }

    public Map<Integer, Long> getParamSpecial() {
        return paramSpecial;
    }

    public void setParamSpecial(Map<Integer, Long> paramSpecial) {
        this.paramSpecial = paramSpecial;
    }
}
