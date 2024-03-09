package com.lypaka.lypakautils.MiscHandlers;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.item.pokeball.PokeBallRegistry;
import com.pixelmonmod.pixelmon.api.pokemon.species.Species;
import com.pixelmonmod.pixelmon.api.pokemon.species.gender.Gender;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import com.pixelmonmod.pixelmon.api.storage.NbtKeys;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.api.util.helpers.RandomHelper;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PixelmonHelpers {

    public static Map<String, List<Pokemon>> pokemonTypeMap = new HashMap<>();

    public static List<Pokemon> getTeam (ServerPlayerEntity player) {

        return StorageProxy.getParty(player).getTeam();

    }

    /**
     * Gets the Pokemon at the provided slot from the player's party
     */
    @Nullable
    public static Pokemon getPokemonAtSlot (ServerPlayerEntity player, int slot) {

        return getTeam(player).get(slot);

    }

    /**
     * Finds the first Pokemon in the player's party matching the provided species and form name and palette name, if any
     */
    @Nullable
    public static Pokemon getFirstMatchingPokemon (ServerPlayerEntity player, Species species, String form, String palette) {

        return getFirstMatchingPokemon(player, species.getName(), form, palette);

    }

    /**
     * Finds the first Pokemon in the player's party matching the provided species name, if any
     */
    @Nullable
    public static Pokemon getFirstMatchingPokemon (ServerPlayerEntity player, String species) {

        return getFirstMatchingPokemon(player, species, "default", "default");

    }

    /**
     * Finds the first Pokemon in the player's party matching the provided species name and form name, if any
     */
    @Nullable
    public static Pokemon getFirstMatchingPokemon (ServerPlayerEntity player, String species, String form) {

        return getFirstMatchingPokemon(player, species, form, "default");

    }

    /**
     * Finds the first Pokemon in the player's party matching the provided species name and form name and palette name, if any
     */
    @Nullable
    public static Pokemon getFirstMatchingPokemon (ServerPlayerEntity player, String species, String form, String palette) {

        Pokemon pokemon = null;
        for (Pokemon p : getTeam(player)) {

            if (p != null) {

                if (p.getSpecies().getName().equalsIgnoreCase(species)) {

                    if (!form.equalsIgnoreCase("default")) {

                        if (p.getForm().getName().equalsIgnoreCase(form)) {

                            if (!palette.equalsIgnoreCase("default")) {

                                if (p.getPalette().getName().equalsIgnoreCase(palette)) {

                                    pokemon = p;
                                    break;

                                }

                            } else {

                                pokemon = p;
                                break;

                            }

                        }

                    } else {

                        if (!palette.equalsIgnoreCase("default")) {

                            if (p.getPalette().getName().equalsIgnoreCase(palette)) {

                                pokemon = p;
                                break;

                            }

                        } else {

                            pokemon = p;
                            break;

                        }

                    }

                }

            }

        }

        return pokemon;

    }

    public static Pokemon fixPokemonIVsAndGender (Pokemon pokemon) {

        int[] ivs = new int[6];
        int perfectCount = 0;
        for (int i = 0; i < 6; i++) {

            int value = RandomHelper.getRandomNumberBetween(1, 31);
            ivs[i] = value;
            if (value == 31) perfectCount++;

        }
        if (PixelmonSpecies.isLegendary(pokemon.getSpecies()) || PixelmonSpecies.isMythical(pokemon.getSpecies()) || PixelmonSpecies.isUltraBeast(pokemon.getSpecies())) {

            if (perfectCount < 3) {

                List<Integer> notPerfectIVSlots = new ArrayList<>();
                for (int i = 0; i < 6; i++) {

                    if (ivs[i] != 31) {

                        notPerfectIVSlots.add(i);

                    }

                }

                for (int i = perfectCount; i <= 3; i++) {

                    int slot = RandomHelper.getRandomElementFromList(notPerfectIVSlots);
                    ivs[slot] = 31;
                    notPerfectIVSlots.removeIf(e -> e == slot);

                }

            }

        }
        pokemon.getIVs().fillFromArray(ivs);
        pokemon.setGender(Gender.getRandomGender(pokemon.getForm()));

        return pokemon;

    }

    public static String getActualPokeBallID (ItemStack pokeBall) {

        String ball = "poke_ball";
        if (pokeBall.hasTag()) {

            ball = PokeBallRegistry.getPokeBall(pokeBall.getTag().getString(NbtKeys.POKE_BALL_ID)).getValue().get().getName();

        }

        return "pixelmon:" + ball.replace(" ", "_").toLowerCase();

    }

}
