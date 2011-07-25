Number.prototype.isBetween = function(x, y) {
	if (x < y) {
		return this >= x && this <= y;
	} else {
		return this >= y && this <= x;
	}
}