extends Control	

signal start_game

func show_text(text):
	$Label.text = str(text)
	if not $Label.visible:
		$Label.show()
	
func show_gameover():
	show_text("Game Over")
	yield(get_tree().create_timer(2),"timeout")
	if not $StartButton.visible:
		$StartButton.show()
	show_text("Space Invader")
	$ScoreLabel.hide()

func _on_StartButton_pressed():
	emit_signal("start_game")


func show_components():
	$Label.show()
	$StartButton.show()

func hide_components():
	$Label.hide()
	$StartButton.hide()
	$ScoreLabel.hide()
	$ScoreLabel.show()
