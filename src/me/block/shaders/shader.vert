#version 120

varying vec3 color;

void main(){

	//color = gl_Color.rgb;
	 gl_TexCoord[0] = gl_MultiTexCoord0;
    gl_Position = ftransform();
}