package com.example.twodicepig

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.twodicepig.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var diceRollTotal = 0
    var doubleFlag = false;

    var p1Active:Boolean = true
    var p1TotalScore: Int = 0
    var p1TurnTotalScore: Int = 0

    var p2Active:Boolean = false
    var p2TotalScore: Int = 0
    var p2TurnTotalScore: Int = 0

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_main)
        setContentView(binding.root)

        //var startScore = 0

        binding.rollButton.setOnClickListener {
            var diceValue1 = Random.nextInt(1, 6)
            var imageName = "@drawable/dice" + diceValue1
            var resourceID = resources.getIdentifier(imageName, "drawable", getPackageName())
            binding.diceUno.setImageResource(resourceID)

            var diceValue2 = Random.nextInt(1, 6)
            imageName = "@drawable/dice" + diceValue2
            resourceID = resources.getIdentifier(imageName, "drawable", getPackageName())
            binding.diceDos.setImageResource(resourceID)

            game(diceValue1,diceValue2)
            binding.p1Score.text = p1TotalScore.toString()
            binding.p2Score.text = p2TotalScore.toString()
            checkPlayerWon()
        }

        binding.holdButton.setOnClickListener {
            if(!doubleFlag) {
                if (p1Active) {
                    p1TotalScore += p1TurnTotalScore
                } else if (p2Active) {
                    p2TotalScore += p2TurnTotalScore
                }
            }else if(doubleFlag){
                binding.statusText.text = "Can't Hold Must Roll Again!"
            }

            binding.p1Score.text = p1TotalScore.toString()
            binding.p2Score.text = p2TotalScore.toString()

            switchPlayer()
            checkPlayerWon()

            var resourceID = resources.getIdentifier("@drawable/dice0", "drawable", getPackageName())
            binding.diceUno.setImageResource(resourceID)
            binding.diceDos.setImageResource(resourceID)
        }

    }

    fun switchPlayer(){
        if (p1Active){
            p1Active = false
            p2Active = true

            binding.statusText.text = "It is Player 2's Turn"
            resetPoints()
        } else if (p2Active) {
            p2Active = false
            p1Active = true

            binding.statusText.text = "It is Player 1's Turn"
            resetPoints()
        }
    }

    fun game(dice1: Int, dice2: Int) {

        if (p1Active) {
            if (dice1 !== 1 && dice2 !== 1) {
                diceRollTotal = dice1 + dice2
                p1TurnTotalScore += diceRollTotal
                binding.turnTotal.text = p1TurnTotalScore.toString()
            } else if (dice1 == 1 && dice2 == 1){
                diceRollTotal = 0
                p1TurnTotalScore = 0
                p1TotalScore = 0
                switchPlayer()
            } else if (dice1 == 1 || dice2 == 1) {
                diceRollTotal = 0
                switchPlayer()
            } else if (dice1 == dice2){
                doubleFlag = true
            }
        } else if (p2Active) {
            if (dice1 !== 1 && dice2 !== 1) {
                diceRollTotal = dice1 + dice2
                p2TurnTotalScore += diceRollTotal
                binding.turnTotal.text = p2TurnTotalScore.toString()
            } else if (dice1 == 1 && dice2 == 1) {
                diceRollTotal = 0
                p2TurnTotalScore = 0
                p2TotalScore = 0
                switchPlayer()
            } else if (dice1 == 1 || dice2 == 1) {
                diceRollTotal = 0
                switchPlayer()
            } else if (dice1 == dice2){
                doubleFlag = true
            }
        }
    }

    fun resetPoints(){
        diceRollTotal = 0
        p1TurnTotalScore = 0
        p2TurnTotalScore = 0
        binding.turnTotal.text = "0"
        doubleFlag = false
    }

    fun checkPlayerWon(){
        if(p1TotalScore >= 50){
            binding.statusText.text = "PLAYER 1 WON!"
            resetPoints()
        }else if(p2TotalScore >= 50){
            binding.statusText.text = "PLAYER 2 WON!"
            resetPoints()
        }else if(p1TotalScore >= 50 && p2TotalScore >= 50){
            binding.statusText.text = "ITS A TIE!"
            resetPoints()
        }
    }

}