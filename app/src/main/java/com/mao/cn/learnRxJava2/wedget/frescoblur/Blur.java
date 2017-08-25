//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mao.cn.learnRxJava2.wedget.frescoblur;

import android.graphics.Bitmap;

public class Blur {
    public Blur() {
    }

    public static void convert(Bitmap original, int radius) throws Exception {
        int w = original.getWidth();
        int h = original.getHeight();
        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;
        int[] r = new int[wh];
        int[] g = new int[wh];
        int[] b = new int[wh];
        int[] vmin = new int[Math.max(w, h)];
        int[] vmax = new int[Math.max(w, h)];
        int[] pix = new int[w * h];
        original.getPixels(pix, 0, w, 0, 0, w, h);
        int[] dv = new int[256 * div];

        int i;
        for(i = 0; i < 256 * div; ++i) {
            dv[i] = i / div;
        }

        int yi = 0;
        int yw = 0;

        int rsum;
        int gsum;
        int bsum;
        int x;
        int y;
        int p1;
        int p2;
        for(y = 0; y < h; ++y) {
            bsum = 0;
            gsum = 0;
            rsum = 0;

            for(i = -radius; i <= radius; ++i) {
                int p = pix[yi + Math.min(wm, Math.max(i, 0))];
                rsum += (p & 16711680) >> 16;
                gsum += (p & '\uff00') >> 8;
                bsum += p & 255;
            }

            for(x = 0; x < w; ++x) {
                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];
                if(y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                    vmax[x] = Math.max(x - radius, 0);
                }

                p1 = pix[yw + vmin[x]];
                p2 = pix[yw + vmax[x]];
                rsum += (p1 & 16711680) - (p2 & 16711680) >> 16;
                gsum += (p1 & '\uff00') - (p2 & '\uff00') >> 8;
                bsum += (p1 & 255) - (p2 & 255);
                ++yi;
            }

            yw += w;
        }

        for(x = 0; x < w; ++x) {
            bsum = 0;
            gsum = 0;
            rsum = 0;
            int yp = -radius * w;

            for(i = -radius; i <= radius; ++i) {
                yi = Math.max(0, yp) + x;
                rsum += r[yi];
                gsum += g[yi];
                bsum += b[yi];
                yp += w;
            }

            yi = x;

            for(y = 0; y < h; ++y) {
                pix[yi] = -16777216 | dv[rsum] << 16 | dv[gsum] << 8 | dv[bsum];
                if(x == 0) {
                    vmin[y] = Math.min(y + radius + 1, hm) * w;
                    vmax[y] = Math.max(y - radius, 0) * w;
                }

                p1 = x + vmin[y];
                p2 = x + vmax[y];
                rsum += r[p1] - r[p2];
                gsum += g[p1] - g[p2];
                bsum += b[p1] - b[p2];
                yi += w;
            }
        }

        original.setPixels(pix, 0, w, 0, 0, w, h);
    }
}
