
package dev.solo.player.fill;

import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

import dev.solo.player.Wrapper;
import org.lwjgl.opengl.GL20;

public class ShaderUtil implements Wrapper
{
    private final int programID;
    private static final String vertex = "#version 120\n\nvoid main() {\n    gl_TexCoord[0] = gl_MultiTexCoord0;\n    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n}";
    private static final String roundedRectGradient = "#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color1, color2, color3, color4;\nuniform float radius;\n\n#define NOISE .5/255.0\n\nfloat roundSDF(vec2 p, vec2 b, float r) {\n    return length(max(abs(p) - b , 0.0)) - r;\n}\n\nvec3 createGradient(vec2 coords, vec3 color1, vec3 color2, vec3 color3, vec3 color4) {\n    vec3 color = mix(mix(color1.rgb, color2.rgb, coords.y), mix(color3.rgb, color4.rgb, coords.y), coords.x);\n    color += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898, 78.233))) * 43758.5453));\n    return color;\n}\n\nvoid main() {\n    vec2 st = gl_TexCoord[0].st;\n    vec2 halfSize = rectSize * .5;\n    float smoothedAlpha =  (1.0-smoothstep(0.0, 2., roundSDF(halfSize - (gl_TexCoord[0].st * rectSize), halfSize - radius - 1., radius))) * color1.a;\n    gl_FragColor = vec4(createGradient(st, color1.rgb, color2.rgb, color3.rgb, color4.rgb), smoothedAlpha);\n}";
    private static final String roundRectOutline = "#version 120\n\nuniform vec2 location, rectSize;\nuniform sampler2D textureIn;\nuniform float radius, alpha;\n\nfloat roundedBoxSDF(vec2 centerPos, vec2 size, float radius) {\n    return length(max(abs(centerPos) -size, 0.)) - radius;\n}\n\nvoid main() {\n    float distance = roundedBoxSDF((rectSize * .5) - (gl_TexCoord[0].st * rectSize), (rectSize * .5) - radius - 1., radius);\n    float smoothedAlpha =  (1.0-smoothstep(0.0, 2.0, distance)) * alpha;\n    gl_FragColor = vec4(texture2D(textureIn, gl_TexCoord[0].st).rgb, smoothedAlpha);\n}";
    private static final String roundRectTextured = "#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color, outlineColor;\nuniform float radius, outlineThickness;\n\nfloat roundedSDF(vec2 centerPos, vec2 size, float radius) {\n    return length(max(abs(centerPos) - size + radius, 0.0)) - radius;\n}\n\nvoid main() {\n    float distance = roundedSDF(gl_FragCoord.xy - location - (rectSize * .5), (rectSize * .5) + (outlineThickness *.5) - 1.0, radius);\n    float blendAmount = smoothstep(0., 2., abs(distance) - (outlineThickness * .5));\n    vec4 insideColor = (distance < 0.) ? color : vec4(outlineColor.rgb,  0.0);\n    gl_FragColor = mix(outlineColor, insideColor, blendAmount);\n}";
    private static final String roundedRect = "#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color;\nuniform float radius;\n\nfloat roundSDF(vec2 p, vec2 b, float r) {\n    return length(max(abs(p) - b, 0.0)) - r;\n}\n\nvoid main() {\n    vec2 rectHalf = rectSize * .5;\n    float smoothedAlpha =  (1.0-smoothstep(0.0, 1.0, roundSDF(rectHalf - (gl_TexCoord[0].st * rectSize), rectHalf - radius - 1., radius))) * color.a;\n    gl_FragColor = vec4(color.rgb, smoothedAlpha);\n}";

    public ShaderUtil(final String fragmentShaderLoc) {
        final int program = GL20.glCreateProgram();
        int fragmentShaderID = 0;
        switch (fragmentShaderLoc) {
            case "roundedRect": {
                fragmentShaderID = this.createShader(new ByteArrayInputStream("#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color;\nuniform float radius;\n\nfloat roundSDF(vec2 p, vec2 b, float r) {\n    return length(max(abs(p) - b, 0.0)) - r;\n}\n\nvoid main() {\n    vec2 rectHalf = rectSize * .5;\n    float smoothedAlpha =  (1.0-smoothstep(0.0, 1.0, roundSDF(rectHalf - (gl_TexCoord[0].st * rectSize), rectHalf - radius - 1., radius))) * color.a;\n    gl_FragColor = vec4(color.rgb, smoothedAlpha);\n}".getBytes()), 35632);
                break;
            }
            case "roundRectOutline": {
                fragmentShaderID = this.createShader(new ByteArrayInputStream("#version 120\n\nuniform vec2 location, rectSize;\nuniform sampler2D textureIn;\nuniform float radius, alpha;\n\nfloat roundedBoxSDF(vec2 centerPos, vec2 size, float radius) {\n    return length(max(abs(centerPos) -size, 0.)) - radius;\n}\n\nvoid main() {\n    float distance = roundedBoxSDF((rectSize * .5) - (gl_TexCoord[0].st * rectSize), (rectSize * .5) - radius - 1., radius);\n    float smoothedAlpha =  (1.0-smoothstep(0.0, 2.0, distance)) * alpha;\n    gl_FragColor = vec4(texture2D(textureIn, gl_TexCoord[0].st).rgb, smoothedAlpha);\n}".getBytes()), 35632);
                break;
            }
            case "roundRectTextured": {
                fragmentShaderID = this.createShader(new ByteArrayInputStream("#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color, outlineColor;\nuniform float radius, outlineThickness;\n\nfloat roundedSDF(vec2 centerPos, vec2 size, float radius) {\n    return length(max(abs(centerPos) - size + radius, 0.0)) - radius;\n}\n\nvoid main() {\n    float distance = roundedSDF(gl_FragCoord.xy - location - (rectSize * .5), (rectSize * .5) + (outlineThickness *.5) - 1.0, radius);\n    float blendAmount = smoothstep(0., 2., abs(distance) - (outlineThickness * .5));\n    vec4 insideColor = (distance < 0.) ? color : vec4(outlineColor.rgb,  0.0);\n    gl_FragColor = mix(outlineColor, insideColor, blendAmount);\n}".getBytes()), 35632);
                break;
            }
            case "roundedRectGradient": {
                fragmentShaderID = this.createShader(new ByteArrayInputStream("#version 120\n\nuniform vec2 location, rectSize;\nuniform vec4 color1, color2, color3, color4;\nuniform float radius;\n\n#define NOISE .5/255.0\n\nfloat roundSDF(vec2 p, vec2 b, float r) {\n    return length(max(abs(p) - b , 0.0)) - r;\n}\n\nvec3 createGradient(vec2 coords, vec3 color1, vec3 color2, vec3 color3, vec3 color4) {\n    vec3 color = mix(mix(color1.rgb, color2.rgb, coords.y), mix(color3.rgb, color4.rgb, coords.y), coords.x);\n    color += mix(NOISE, -NOISE, fract(sin(dot(coords.xy, vec2(12.9898, 78.233))) * 43758.5453));\n    return color;\n}\n\nvoid main() {\n    vec2 st = gl_TexCoord[0].st;\n    vec2 halfSize = rectSize * .5;\n    float smoothedAlpha =  (1.0-smoothstep(0.0, 2., roundSDF(halfSize - (gl_TexCoord[0].st * rectSize), halfSize - radius - 1., radius))) * color1.a;\n    gl_FragColor = vec4(createGradient(st, color1.rgb, color2.rgb, color3.rgb, color4.rgb), smoothedAlpha);\n}".getBytes()), 35632);
                break;
            }
            default: {
                fragmentShaderID = 0;
                break;
            }
        }
        GL20.glAttachShader(program, fragmentShaderID);
        final int vertexShaderID = this.createShader(new ByteArrayInputStream("#version 120\n\nvoid main() {\n    gl_TexCoord[0] = gl_MultiTexCoord0;\n    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n}".getBytes()), 35633);
        GL20.glAttachShader(program, vertexShaderID);
        GL20.glLinkProgram(program);
        final int status = GL20.glGetProgrami(program, 35714);
        if (status == 0) {
            throw new IllegalStateException("Shader failed to link!");
        }
        this.programID = program;
    }

    public void init() {
        GL20.glUseProgram(this.programID);
    }

    public void unload() {
        GL20.glUseProgram(0);
    }

    public int getUniform(final String name) {
        return GL20.glGetUniformLocation(this.programID, name);
    }

    public void setUniformf(final String name, final float... args) {
        final int loc = GL20.glGetUniformLocation(this.programID, name);
        switch (args.length) {
            case 1: {
                GL20.glUniform1f(loc, args[0]);
                break;
            }
            case 2: {
                GL20.glUniform2f(loc, args[0], args[1]);
                break;
            }
            case 3: {
                GL20.glUniform3f(loc, args[0], args[1], args[2]);
                break;
            }
            case 4: {
                GL20.glUniform4f(loc, args[0], args[1], args[2], args[3]);
                break;
            }
        }
    }

    public void setUniformi(final String name, final int... args) {
        final int loc = GL20.glGetUniformLocation(this.programID, name);
        if (args.length > 1) {
            GL20.glUniform2i(loc, args[0], args[1]);
        }
        else {
            GL20.glUniform1i(loc, args[0]);
        }
    }

    public static void drawQuads(final float x, final float y, final float width, final float height) {
        GL20.glBegin(7);
        GL20.glTexCoord2f(0.0f, 0.0f);
        GL20.glVertex2f(x, y);
        GL20.glTexCoord2f(0.0f, 1.0f);
        GL20.glVertex2f(x, y + height);
        GL20.glTexCoord2f(1.0f, 1.0f);
        GL20.glVertex2f(x + width, y + height);
        GL20.glTexCoord2f(1.0f, 0.0f);
        GL20.glVertex2f(x + width, y);
        GL20.glEnd();
    }

    public static void drawQuads() {
        drawQuads(0.0f, 0.0f, (float)ShaderUtil.mc.getWindow().getGuiScaledWidth(), (float)ShaderUtil.mc.getWindow().getGuiScaledHeight());
    }

    private int createShader(final InputStream inputStream, final int shaderType) {
        final int shader = GL20.glCreateShader(shaderType);
        GL20.glShaderSource(shader, this.readInputStream(inputStream));
        GL20.glCompileShader(shader);
        if (GL20.glGetShaderi(shader, 35713) == 0) {
            System.out.println(GL20.glGetShaderInfoLog(shader, 4096));
            throw new IllegalStateException(String.format("Shader (%s) failed to compile!", shaderType));
        }
        return shader;
    }

    private String readInputStream(final InputStream inputStream) {
        final StringBuilder stringBuilder = new StringBuilder();
        try {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
        }
        catch (final Exception ex) {}
        return stringBuilder.toString();
    }
}
