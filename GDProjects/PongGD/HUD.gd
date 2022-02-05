extends CanvasLayer
signal start

func _on_Button_pressed():
	emit_signal("start")
	$Title.hide()
	$Button.hide()
