package net.spell_power.api;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;

import java.util.function.Consumer;

import static net.spell_power.api.MagicSchool.FIRE;

public class SpellDamageSource extends DamageSources {
    public static SpellDamageSource create(MagicSchool school, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity player) {
            return player(school, player);
        } else {
            return mob(school, attacker);
        }
    }

    public static SpellDamageSource mob(MagicSchool school, LivingEntity attacker) {
        return SpellDamageSource.create(school, "mob", attacker);
    }

    public static SpellDamageSource player(MagicSchool school, PlayerEntity attacker) {
        return SpellDamageSource.create(school, "player", attacker);
    }

    private static SpellDamageSource create(MagicSchool school, String name, Entity source) {
        var damageSource = new SpellDamageSource(name, source, school);
        school.damageSourceConfigurator().accept(damageSource);
        if (school == FIRE) {
            damageSource.onFire();
        }
        return damageSource;
    }

    public static class Configurator {
        public static Consumer<SpellDamageSource> MAGIC = source -> {
            source.magic();
        };

        public static Consumer<SpellDamageSource> MELEE = source -> {
        };
    }

    private MagicSchool school;

    public SpellDamageSource(String name, Entity source, MagicSchool school) {
        super(name, source);
        this.school = school;
    }

    public MagicSchool getMagicSchool() {
        return school;
    }

    @Override
    public DamageSource onFire() {
        return super.onFire();
    }
}
