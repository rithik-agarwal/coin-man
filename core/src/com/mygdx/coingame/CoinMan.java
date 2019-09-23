package com.mygdx.coingame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public class CoinMan extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture[] man;
	Texture dizzy;
	int ms=0;
	int p=0;
	int game=0;
	int many=0;
	float decider;
	float factor=0.2f;
	Random rand=new Random();
	ArrayList<Integer>  coinx=new ArrayList<>();
	ArrayList<Integer> coiny=new ArrayList<>();
	Rectangle manR;

	Texture coin;
	int coincount=0;
	ArrayList<Integer>  bombx=new ArrayList<>();
	ArrayList<Integer> bomby=new ArrayList<>();
	ArrayList<Rectangle> coinrec=new ArrayList<>();
	ArrayList<Rectangle> bombrec=new ArrayList<>();
	Texture bomb;
	int score=0;
	int bombcount=0;
	BitmapFont font;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background=new Texture("bg.png");
		man=new Texture[4];
		man[0]=new Texture("frame-1.png");
		man[1]=new Texture("frame-2.png");
		man[2]=new Texture("frame-3.png");
		man[3]=new Texture("frame-4.png");
		coin=new Texture("coin.png");
		bomb=new Texture("bomb.png");
		dizzy=new Texture("dizzy.png");
		manR=new Rectangle();
		font=new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().scale(10);

	}

	public void addcoin()
	{
		float n=rand.nextFloat();


		float height = n * Gdx.graphics.getHeight();
		coiny.add((int)height);
		coinx.add(Gdx.graphics.getWidth());
	}
	public void addBomb()
	{	float height = rand.nextFloat() * Gdx.graphics.getHeight();
		bomby.add((int)height);
		bombx.add(Gdx.graphics.getWidth());

	}



	@Override
	public void render () {
		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		if(game != 2)
		batch.draw(man[ms],Gdx.graphics.getWidth()/2-man[0].getWidth(),many);
		else if(game==2)
		{
			batch.draw(dizzy,Gdx.graphics.getWidth()/2-man[0].getWidth(),many);
		}
		manR.set(Gdx.graphics.getWidth()/2-man[0].getWidth(),many,man[ms].getWidth(),man[ms].getHeight());

		if(game==1)
		{
			if(coincount < 100)
			{
				coincount++;
			}
			else
			{
				coincount=0;
				addcoin();
			}
			coinrec.clear();
			for(int i=0;i<coinx.size();i++)
			{
				batch.draw(coin,coinx.get(i),coiny.get(i));
				if(score < 10)
				coinx.set(i,coinx.get(i)-4);
				else if(score < 20)
					coinx.set(i,coinx.get(i)-8);
				else if(score < 30)
					coinx.set(i,coinx.get(i)-11);




					coinrec.add(new Rectangle(coinx.get(i),coiny.get(i),coin.getWidth(),coin.getHeight()));
			}
			if(bombcount < 250)
			{
				bombcount++;
			}
			else
			{
				bombcount=0;
				addBomb();
			}
			bombrec.clear();
			for(int i=0;i<bombx.size();i++)
			{
				batch.draw(bomb,bombx.get(i),bomby.get(i));
				if(score < 10)
				bombx.set(i,bombx.get(i)-7);
				else if(score < 20)
					bombx.set(i,bombx.get(i)-8);
				else if(score < 30)
					bombx.set(i,bombx.get(i)-11);



					bombrec.add(new Rectangle(bombx.get(i),bomby.get(i),bomb.getWidth(),bomb.getHeight()));
			}

			if(Gdx.input.justTouched())
			{
				decider=-10;

			}

			if(p < 8)
			{
				p++;
			}
			else
			{
				p=0;
				if(ms < 3)
					ms++;
				else
					ms=0;
			}
			decider += factor;
			many -= decider;

			if(many <=0)
				many=0;


		}
		else
			if(game==0)
			{
				if(Gdx.input.justTouched())
					game=1;

			}
			else
				if(game==2)
				{
					if(Gdx.input.justTouched())
					{	game=1;
					score=0;}

					coinx.clear();
					coiny.clear();
					coinrec.clear();
					coincount=0;
					bombx.clear();
					bomby.clear();
					bombrec.clear();
					bombcount=0;

				}
		for(int i=0;i<coinrec.size();i++)
		{
			if(Intersector.overlaps(manR,coinrec.get(i)))
			{
				Gdx.app.log("test","collision!!");
				coinx.remove(i);
				coiny.remove(i);
				coinrec.remove(i);
				score++;

			}
		}
		for(int i=0;i<bombrec.size();i++)
		{
			if(Intersector.overlaps(manR,bombrec.get(i)))
			{
				Gdx.app.log("test","collision!!");
				game=2;
			}
		}



		font.draw(batch,String.valueOf(score),100,Gdx.graphics.getHeight()-15);





		batch.end();


	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}}

