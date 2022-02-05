extends Node2D

var opponent_score = 0
var player_score = 0

func start_game():
	$CountDownTimer.start()
	$Ball.show()
	$Timer.show()
	$PlayerScore.show()
	$OpponentScore.show()

func _process(delta):
	$PlayerScore.text = str(player_score)
	$OpponentScore.text = str(opponent_score)
	if $Timer.visible:
		$Timer.text = str(int($CountDownTimer.time_left) + 1)


func _on_Left_body_entered(body):
	game_over(false)


func _on_Right_body_entered(body):
	game_over(true)


func _on_CountDownTimer_timeout():
	$Timer.set_deferred("visible",false)
	$Ball.reset()
	
func game_over(opponent_scored):
	$ScoreSound.play()
	$Ball.stop_ball()
	$Player.reset()
	$Opponent.reset()
	if opponent_scored:
		opponent_score += 1
	else:
		player_score += 1
	$Timer.show()
	$CountDownTimer.start()
