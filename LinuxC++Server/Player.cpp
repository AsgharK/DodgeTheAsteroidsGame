class Player {
	protected: 
        static const int mapHeight = 480;
        static const int mapWidth = 640;
        double x, y;
	    double moveSpeed;
	    int width, height;
        bool left, right, down, up;
	
	public:
        Player() {
		    width = 50;
		    height = 70;

		    moveSpeed = 2;
	    }

	    void setPosition(int init_x, int init_y) {
		    x = init_x;
		    y = init_y;
	    }

	    void setLeft(bool s) {
		    left = s;
	    }

	    void setRight(bool s) {
	    	right = s;
	    }

	    void setUp(bool s) {
		    up = s;
	    }

	    void setDown(bool s) {
		    down = s;
	    }
        int getX() {
		    return x;
	    }
        int getY() {
		    return y;
	    }
        void update() {
		    getNextPosition();
	    }
        void setAll() {
		    left=false;
            right=false;
            down=false;
            up=false;
	    }

	private: 
        void getNextPosition() {
		    if (left) {
			    x = x - moveSpeed;
			    if (x < width / 2) {
				    x = width / 2;
			    }
		    }
		    else if (right) {
			    x = x + moveSpeed;
			    if (x > mapWidth - width / 2) {
				    x = mapWidth - width / 2;
			    }
		    }
		    else if (up) {
			    y = y - moveSpeed;
			    if (y < height / 2) {
				    y = height / 2;
			    }
		    }
		    else if (down) {
			    y = y + moveSpeed;
			    if (y > mapHeight - height / 2) {
				    y = mapHeight - height / 2;
			    }
	   	    }
        }
};
