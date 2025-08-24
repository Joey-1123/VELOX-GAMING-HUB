plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	id("org.jetbrains.kotlin.plugin.compose")
}

android {
	namespace = "dev.clover.gamehub"
	compileSdk = 35

	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}

	kotlinOptions {
		jvmTarget = "17"
	}

	defaultConfig {
		applicationId = "dev.clover.gamehub"
		minSdk = 26
		targetSdk = 35
		versionCode = 1
		versionName = "0.1.0"
		vectorDrawables.useSupportLibrary = true
	}

	buildTypes {
		release {
			isMinifyEnabled = true
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
		debug {
			applicationIdSuffix = ".debug"
			versionNameSuffix = "+debug"
		}
	}

	flavorDimensions += listOf("dist")
	productFlavors {
		create("standalone") {
			dimension = "dist"
			applicationIdSuffix = ".standalone"
		}
		create("system") {
			dimension = "dist"
			applicationIdSuffix = ".system"
		}
	}

	buildFeatures {
		compose = true
	}

	composeOptions {
		kotlinCompilerExtensionVersion = "1.7.3"
	}

	packaging {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
		}
	}

	buildToolsVersion = "35.0.0"
}

dependencies {
	implementation(platform("androidx.compose:compose-bom:2024.09.03"))
	implementation("androidx.core:core-ktx:1.13.1")
	implementation("androidx.activity:activity-compose:1.9.3")
	implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")
	implementation("androidx.compose.ui:ui")
	implementation("androidx.compose.ui:ui-tooling-preview")
	implementation("androidx.compose.material3:material3:1.3.0")
	implementation("com.google.android.material:material:1.12.0")
	implementation("androidx.navigation:navigation-compose:2.8.3")
	implementation("androidx.compose.material:material-icons-extended:1.7.3")

	debugImplementation("androidx.compose.ui:ui-tooling")
	debugImplementation("androidx.compose.ui:ui-test-manifest")
}