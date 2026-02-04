package com.skydiveforecast.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.skydiveforecast", importOptions = ImportOption.DoNotIncludeTests.class)
class HexagonalArchitectureTest {

    @ArchTest
    static final ArchRule portsShouldBeInterfaces =
            classes()
                    .that().resideInAPackage("..domain.port..")
                    .should().beInterfaces();

    @ArchTest
    static final ArchRule controllersShouldOnlyBeInWebAdapter =
            classes()
                    .that().haveSimpleNameEndingWith("Controller")
                    .should().resideInAPackage("..infrastructure.adapter.in.web..");

    @ArchTest
    static final ArchRule jpaEntitiesShouldOnlyBeInPersistence =
            classes()
                    .that().haveSimpleNameEndingWith("Entity")
                    .should().resideInAPackage("..infrastructure.persistance.entity..");
}
