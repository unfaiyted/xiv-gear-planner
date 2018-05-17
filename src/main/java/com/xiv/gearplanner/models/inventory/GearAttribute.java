package com.xiv.gearplanner.models.inventory;

import javax.persistence.*;

@Entity
@Table
public class GearAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//      "auto_attack": 7.33,
    @OneToOne
    private Gear gear;

    @Column
    private Float autoAttack;
//              "auto_attack_hq": 8,
    @Column
    private Float autoAttackHQ;
//              "block_rate": 0,
    @Column
    private Float blockRate;
    //              "block_rate_hq": 0,
    @Column
    private Float blockRateHQ;
//              "block_strength": 0,
    @Column
    private Float blockStrength;
//              "block_strength_hq": 0,
//              "damage": 11,
    @Column
    private Float damage;
//              "damage_hq": 12,
    @Column
    private Float damageHQ;
//              "defense": 0,
    @Column
    private Float defense;
//              "defense_hq": 0,
    @Column
    private Float defenseHQ;
//              "delay": 2,
    @Column
    private Float delay;
//              "delay_hq": 2,
    @Column
    private Float delayHQ;
//              "dps": 3.66,
    @Column
    private Float dps;
//              "dps_hq": 4,
    @Column
    private Float dpsHQ;
//              "id": 105,
//              "magic_damage": 10
    @Column
    private Float magicDamage;
//              "magic_damage_hq": 12,
    @Column
    private Float magicDamageHQ;
//              "magic_defense": 0,
    @Column
    private Float magicDefense;
//              "magic_defense_hq": 0,
    @Column
    private Float magicDefenseHQ;

    public GearAttribute(Long id, Float autoAttack, Float autoAttackHQ, Float blockRate,
                         Float blockRateHQ, Float blockStrength, Float damage, Float damageHQ,
                         Float defense, Float defenseHQ, Float delay, Float delayHQ, Float dps,
                         Float dpsHQ, Float magicDamage, Float magicDamageHQ, Float magicDefense,
                         Float magicDefenseHQ) {
        this.id = id;
        this.autoAttack = autoAttack;
        this.autoAttackHQ = autoAttackHQ;
        this.blockRate = blockRate;
        this.blockRateHQ = blockRateHQ;
        this.blockStrength = blockStrength;
        this.damage = damage;
        this.damageHQ = damageHQ;
        this.defense = defense;
        this.defenseHQ = defenseHQ;
        this.delay = delay;
        this.delayHQ = delayHQ;
        this.dps = dps;
        this.dpsHQ = dpsHQ;
        this.magicDamage = magicDamage;
        this.magicDamageHQ = magicDamageHQ;
        this.magicDefense = magicDefense;
        this.magicDefenseHQ = magicDefenseHQ;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getAutoAttack() {
        return autoAttack;
    }

    public void setAutoAttack(Float autoAttack) {
        this.autoAttack = autoAttack;
    }

    public Float getAutoAttackHQ() {
        return autoAttackHQ;
    }

    public void setAutoAttackHQ(Float autoAttackHQ) {
        this.autoAttackHQ = autoAttackHQ;
    }

    public Float getBlockRate() {
        return blockRate;
    }

    public void setBlockRate(Float blockRate) {
        this.blockRate = blockRate;
    }

    public Float getBlockRateHQ() {
        return blockRateHQ;
    }

    public void setBlockRateHQ(Float blockRateHQ) {
        this.blockRateHQ = blockRateHQ;
    }

    public Float getBlockStrength() {
        return blockStrength;
    }

    public void setBlockStrength(Float blockStrength) {
        this.blockStrength = blockStrength;
    }

    public Float getDamage() {
        return damage;
    }

    public void setDamage(Float damage) {
        this.damage = damage;
    }

    public Float getDamageHQ() {
        return damageHQ;
    }

    public void setDamageHQ(Float damageHQ) {
        this.damageHQ = damageHQ;
    }

    public Float getDefense() {
        return defense;
    }

    public void setDefense(Float defense) {
        this.defense = defense;
    }

    public Float getDefenseHQ() {
        return defenseHQ;
    }

    public void setDefenseHQ(Float defenseHQ) {
        this.defenseHQ = defenseHQ;
    }

    public Float getDelay() {
        return delay;
    }

    public void setDelay(Float delay) {
        this.delay = delay;
    }

    public Float getDelayHQ() {
        return delayHQ;
    }

    public void setDelayHQ(Float delayHQ) {
        this.delayHQ = delayHQ;
    }

    public Float getDps() {
        return dps;
    }

    public void setDps(Float dps) {
        this.dps = dps;
    }

    public Float getDpsHQ() {
        return dpsHQ;
    }

    public void setDpsHQ(Float dpsHQ) {
        this.dpsHQ = dpsHQ;
    }

    public Float getMagicDamage() {
        return magicDamage;
    }

    public void setMagicDamage(Float magicDamage) {
        this.magicDamage = magicDamage;
    }

    public Float getMagicDamageHQ() {
        return magicDamageHQ;
    }

    public void setMagicDamageHQ(Float magicDamageHQ) {
        this.magicDamageHQ = magicDamageHQ;
    }

    public Float getMagicDefense() {
        return magicDefense;
    }

    public void setMagicDefense(Float magicDefense) {
        this.magicDefense = magicDefense;
    }

    public Float getMagicDefenseHQ() {
        return magicDefenseHQ;
    }

    public void setMagicDefenseHQ(Float magicDefenseHQ) {
        this.magicDefenseHQ = magicDefenseHQ;
    }

    @Override
    public String toString() {
        return "GearAttribute{" +
                "id=" + id +
                ", autoAttack=" + autoAttack +
                ", autoAttackHQ=" + autoAttackHQ +
                ", blockRate=" + blockRate +
                ", blockRateHQ=" + blockRateHQ +
                ", blockStrength=" + blockStrength +
                ", damage=" + damage +
                ", damageHQ=" + damageHQ +
                ", defense=" + defense +
                ", defenseHQ=" + defenseHQ +
                ", delay=" + delay +
                ", delayHQ=" + delayHQ +
                ", dps=" + dps +
                ", dpsHQ=" + dpsHQ +
                ", magicDamage=" + magicDamage +
                ", magicDamageHQ=" + magicDamageHQ +
                ", magicDefense=" + magicDefense +
                ", magicDefenseHQ=" + magicDefenseHQ +
                '}';
    }
}
