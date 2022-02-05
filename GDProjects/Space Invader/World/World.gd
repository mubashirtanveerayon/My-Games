extends Node2D

export var enemy_scene: PackedScene

var enemy_spawn_flag = false

var score = 1

func _on_EnemySpawnTimer_timeout():
	var enemy = enemy_scene.instance()
	add_child(enemy)
	enemy.spawn(enemy_spawn_flag)
	enemy_spawn_flag = !enemy_spawn_flag
	
func _process(delta):
	$HUD/ScoreLabel.text = str(score)


func start_game():
	score = 1
	get_tree().call_group("enemies","queue_free")
	get_tree().call_group("bullets","queue_free")
	$Player.reset()
	$EnemySpawnTimer.start()
	$HUD.hide_components()
	$ScoreTimer.start()


func _on_Player_dead():
	$EnemySpawnTimer.stop()
	$HUD.show_gameover()
	$ScoreTimer.stop()


func _on_ScoreTimer_timeout():
	score += 1
	
func _on_Enemy_passed():
	score -= 5
	
func _on_Enemy_killed():
	score += 5

