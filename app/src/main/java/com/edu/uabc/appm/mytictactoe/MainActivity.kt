package com.edu.uabc.appm.mytictactoe

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var boardView: BoardView? = null
    private var gameEngine: GameEngine? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        boardView = board as BoardView
        gameEngine = GameEngine()
        boardView!!.setGameEngine(gameEngine!!)
        boardView!!.setMainActivity(this)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId === R.id.action_new_game) {
            newGame()
        }

        return super.onOptionsItemSelected(item)
    }

    fun gameEnded(c: Char) {
        val msg = if (c == 'T') "Game Ended. Tie" else "GameEnded. $c win"

        AlertDialog.Builder(this).setTitle("Tic Tac Toe").setMessage(msg)
            .setOnDismissListener(DialogInterface.OnDismissListener { newGame() }).show()
    }

    private fun newGame() {
        gameEngine?.newGame()
        boardView?.invalidate()
    }

}
