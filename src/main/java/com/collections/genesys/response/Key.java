package com.collections.genesys.response;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Key {

	@JsonProperty(value = "kid")
	public String kid;
	@JsonProperty(value = "kty")
	public String kty;
	@JsonProperty(value = "alg")
	public String alg;
	@JsonProperty(value = "use")
	public String use;
	@JsonProperty(value = "n")
	public String n;
	@JsonProperty(value = "e")
	public String e;
	@JsonProperty(value = "x5c")
	public ArrayList<String> x5c;
	@JsonProperty(value = "x5t")
	public String x5t;
	@JsonProperty(value = "x5t#S256")
	public String x5;

	public String getKid() {
		return kid;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	public String getKty() {
		return kty;
	}

	public void setKty(String kty) {
		this.kty = kty;
	}

	public String getAlg() {
		return alg;
	}

	public void setAlg(String alg) {
		this.alg = alg;
	}

	public String getUse() {
		return use;
	}

	public void setUse(String use) {
		this.use = use;
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public String getE() {
		return e;
	}

	public void setE(String e) {
		this.e = e;
	}

	public ArrayList<String> getX5c() {
		return x5c;
	}

	public void setX5c(ArrayList<String> x5c) {
		this.x5c = x5c;
	}

	public String getX5t() {
		return x5t;
	}

	public void setX5t(String x5t) {
		this.x5t = x5t;
	}

	public String getX5() {
		return x5;
	}

	public void setX5(String x5) {
		this.x5 = x5;
	}

	@Override
	public String toString() {
		return "Key [kid=" + kid + ", kty=" + kty + ", alg=" + alg + ", use=" + use + ", n=" + n + ", e=" + e + ", x5c="
				+ x5c + ", x5t=" + x5t + ", x5=" + x5 + "]";
	}

}
