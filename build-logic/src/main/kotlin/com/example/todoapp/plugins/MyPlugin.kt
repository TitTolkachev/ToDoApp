package ru.yandex.shmr24.plugins

import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Action
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.newInstance
import org.gradle.kotlin.dsl.register
import ru.yandex.shmr24.tasks.CheckArtifactsTask
import ru.yandex.shmr24.tasks.GenerateNameTask
import ru.yandex.shmr24.tasks.Repository
import ru.yandex.shmr24.tasks.SayHelloUserTask
import ru.yandex.shmr24.tasks.TaskWithDependencies
import java.io.File
import javax.inject.Inject

class MyPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val androidComponents =
            project.extensions.findByType(AndroidComponentsExtension::class.java)
                ?: throw GradleException("android plugin required.")

        androidComponents.onVariants { variant ->
            println("Variant ${variant.name}")
            val artifacts = variant.artifacts.get(SingleArtifact.APK)
            project.tasks.register("checkDirFor${variant.name}", CheckArtifactsTask::class) {
                apkDir.set(artifacts)
            }
        }

        val extension = project.extensions.create("myPlugin", MyPluginExtension::class)
        project.configureDemoTasks()
        project.afterEvaluate {
            project.configureDepsTask(extension)
        }
    }

    private fun Project.configureDepsTask(extension: MyPluginExtension) {
        tasks.register<TaskWithDependencies>("depsTask", Repository()).apply {
            configure {
                enabled = extension.myDeps.enable.getOrElse(true)
                prefix.set(extension.myDeps.prefix)
            }
        }
    }

    private fun Project.configureDemoTasks() {
        val userNameTask = tasks.register("userName", GenerateNameTask::class) {
            userNameFile.set(File("userName.txt"))
        }
        tasks.register("helloUser", SayHelloUserTask::class) {
            userNameFile.set(userNameTask.get().userNameFile)
        }
    }
}

abstract class MyPluginExtension @Inject constructor(objectFactory: ObjectFactory) {
    val myDeps = objectFactory.newInstance<MyDepsExtension>()

    fun myDeps(action: Action<MyDepsExtension>) {
        action.execute(myDeps)
    }
}

interface MyDepsExtension {
    val enable: Property<Boolean>
    val prefix: Property<String>
}