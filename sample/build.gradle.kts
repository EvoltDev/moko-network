/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

subprojects {
    configurations.all {
        resolutionStrategy.dependencySubstitution {
            substitute(module(Deps.Libs.MultiPlatform.mokoNetwork))
                .with(project(":network"))
            substitute(module(Deps.Libs.MultiPlatform.mokoNetworkErrors))
                .with(project(":network-errors"))
            substitute(module(Deps.Libs.MultiPlatform.mokoNetworkBignum))
                .with(project(":network-bignum"))
        }
    }
}
