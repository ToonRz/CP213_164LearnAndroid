package com.example.a164_lablearnandriod

import com.example.a164_lablearnandriod.utils.*
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `PokemonSpecies holds name and url`() {
        val s = PokemonSpecies("pikachu", "https://pokeapi.co/25/")
        assertEquals("pikachu", s.name)
        assertEquals("https://pokeapi.co/25/", s.url)
    }

    @Test
    fun `PokemonEntry holds entry number and species`() {
        val entry = PokemonEntry(1, PokemonSpecies("bulbasaur", "u"))
        assertEquals(1, entry.entry_number)
        assertEquals("bulbasaur", entry.pokemon_species.name)
    }

    @Test
    fun `PokedexResponse holds list of entries`() {
        val entries = listOf(
            PokemonEntry(1, PokemonSpecies("bulbasaur", "u1")),
            PokemonEntry(4, PokemonSpecies("charmander", "u2"))
        )
        val resp = PokedexResponse(entries)
        assertEquals(2, resp.pokemon_entries.size)
        assertEquals("charmander", resp.pokemon_entries[1].pokemon_species.name)
    }

    @Test
    fun `fetchPokemon success updates pokemonList`() = runTest {
        mockkObject(PokemonNetwork)
        val mockApi = mockk<PokemonApiService>()
        every { PokemonNetwork.api } returns mockApi

        val data = listOf(
            PokemonEntry(1, PokemonSpecies("bulbasaur", "u")),
            PokemonEntry(4, PokemonSpecies("charmander", "u"))
        )
        coEvery { mockApi.getKantoPokedex() } returns PokedexResponse(data)

        val vm = PokemonViewModel()
        testScheduler.advanceUntilIdle()

        assertEquals(2, vm.pokemonList.value.size)
        assertEquals("bulbasaur", vm.pokemonList.value[0].pokemon_species.name)
    }

    @Test
    fun `fetchPokemon failure keeps list empty`() = runTest {
        mockkObject(PokemonNetwork)
        val mockApi = mockk<PokemonApiService>()
        every { PokemonNetwork.api } returns mockApi
        coEvery { mockApi.getKantoPokedex() } throws RuntimeException("Err")

        val vm = PokemonViewModel()
        testScheduler.advanceUntilIdle()

        assertTrue(vm.pokemonList.value.isEmpty())
    }
}
