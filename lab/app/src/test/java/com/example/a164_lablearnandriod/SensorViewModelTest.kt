package com.example.a164_lablearnandriod

import com.example.a164_lablearnandriod.utils.*
import org.junit.Assert.*
import org.junit.Test

/**
 * Additional data model tests.
 */
class AdditionalDataModelTest {

    @Test
    fun `PokedexResponse with empty list`() {
        val resp = PokedexResponse(emptyList())
        assertTrue(resp.pokemon_entries.isEmpty())
    }

    @Test
    fun `PokemonSpecies equality`() {
        val a = PokemonSpecies("pikachu", "url")
        val b = PokemonSpecies("pikachu", "url")
        assertEquals(a, b)
    }
}
