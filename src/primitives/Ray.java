package primitives;

import java.util.Objects;

public class Ray {
	Point3D _p0;
	Vector _dir;

	public Ray(Point3D _p0, Vector _dir) {
		this._p0 = _p0;
		this._dir = _dir.normalized();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Ray)) return false;
		Ray ray = (Ray) o;
		return _p0.equals(ray._p0) && _dir.equals(ray._dir);
	}
}
