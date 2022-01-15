package com.rodrigoads.rickandmortyapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rodrigoads.rickandmortyapp.R
import com.rodrigoads.rickandmortyapp.databinding.CharacterItemListBinding
import com.rodrigoads.rickandmortyapp.glide.GlideManager
import com.rodrigoads.rickandmortyapp.interfaces.getObjectInfos
import com.rodrigoads.rickandmortyapp.model.api.character.Character

class CharacterAdapter(
    val list: List<Character>,
    val getObjectInfos: getObjectInfos,
    val context: Context
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding =
            CharacterItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding, getObjectInfos, context)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class CharacterViewHolder(
        private val characterItemListBinding: CharacterItemListBinding,
        private val getObjectInfos: getObjectInfos,
        private val context: Context
    ) : RecyclerView.ViewHolder(characterItemListBinding.root) {
        fun bind(character: Character) {
            characterItemListBinding.textViewCharacterName.text = character.characterName
            GlideManager().setImage(
                characterItemListBinding.imageCharacter,
                character.characterImage
            )

            when (character.characterGender) {
                "Female" -> {
                    characterItemListBinding.textViewStatus.text = context.getString(R.string.main)
                }
                "Male" -> {
                    characterItemListBinding.textViewStatus.text = context.getString(R.string.male)
                }
                "Genderless" -> {
                    characterItemListBinding.textViewStatus.text =
                        context.getString(R.string.genderless)
                }
                else -> {
                    characterItemListBinding.textViewStatus.text =
                        context.getString(R.string.unknown)
                }
            }
            characterItemListBinding.root.setOnClickListener {
                getObjectInfos.getCharacter(character)
            }
        }

    }
}