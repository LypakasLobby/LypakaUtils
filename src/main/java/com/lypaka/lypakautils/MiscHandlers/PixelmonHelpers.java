package com.lypaka.lypakautils.MiscHandlers;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.species.Species;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import net.minecraft.server.level.ServerPlayer;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class PixelmonHelpers {

    public static Map<String, List<Pokemon>> pokemonTypeMap = new HashMap<>();

    public static List<Pokemon> getTeam (ServerPlayer player) throws ExecutionException, InterruptedException {

        return StorageProxy.getParty(player).get().getTeam();

    }

    /**
     * Gets the Pokemon at the provided slot from the player's party
     */
    @Nullable
    public static Pokemon getPokemonAtSlot (ServerPlayer player, int slot) throws ExecutionException, InterruptedException {

        return getTeam(player).get(slot);

    }

    /**
     * Finds the first Pokemon in the player's party matching the provided species and form name and palette name, if any
     */
    @Nullable
    public static Pokemon getFirstMatchingPokemon (ServerPlayer player, Species species, String form, String palette) throws ExecutionException, InterruptedException {

        return getFirstMatchingPokemon(player, species.getName(), form, palette);

    }

    /**
     * Finds the first Pokemon in the player's party matching the provided species name, if any
     */
    @Nullable
    public static Pokemon getFirstMatchingPokemon (ServerPlayer player, String species) throws ExecutionException, InterruptedException {

        return getFirstMatchingPokemon(player, species, "default", "default");

    }

    /**
     * Finds the first Pokemon in the player's party matching the provided species name and form name, if any
     */
    @Nullable
    public static Pokemon getFirstMatchingPokemon (ServerPlayer player, String species, String form) throws ExecutionException, InterruptedException {

        return getFirstMatchingPokemon(player, species, form, "default");

    }

    /**
     * Finds the first Pokemon in the player's party matching the provided species name and form name and palette name, if any
     */
    @Nullable
    public static Pokemon getFirstMatchingPokemon (ServerPlayer player, String species, String form, String palette) throws ExecutionException, InterruptedException {

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

}
