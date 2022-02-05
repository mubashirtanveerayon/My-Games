extends CanvasLayer


signal start_game


func update_score(score):
	$ScoreLabel.text = str(score)
	
func show_message(text):
	$MessageLabel.text = str(text)
	$MessageLabel.show()
	$MessageTimer.start()
	
func show_game_over():
	show_message("Game over")
	yield($MessageTimer,"timeout")
	$MessageLabel.text = str("Dodge The Creeps")
	$MessageLabel.show()
	yield(get_tree().create_timer(1.0),"timeout")
	$StartButton.show()
	

func _on_StartButton_pressed():
	$StartButton.hide()
	emit_signal("start_game")


func _on_MessageTimer_timeout():
	$MessageLabel.hide()
