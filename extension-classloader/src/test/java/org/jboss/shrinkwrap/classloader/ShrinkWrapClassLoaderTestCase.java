/*
 * JBoss, Home of Professional Open Source
 * Copyright 2009, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.shrinkwrap.classloader;

import java.net.URL;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;

/**
 * ShrinkWrapClassLoaderTestCase
 *
 * @author <a href="mailto:aslak@redhat.com">Aslak Knutsen</a>
 * @version $Revision: $
 */
public class ShrinkWrapClassLoaderTestCase
{

   private Archive<?> createArchive() 
   {
      return ShrinkWrap.create(JavaArchive.class)
                              .as(ExplodedImporter.class)
                              .importDirectory("target/test-classes")
                              .as(JavaArchive.class);
   }
   
   @Test
   public void shouldBeAbleToLoadClassFromArchive() throws Exception
   {
      ShrinkWrapClassLoader archiveClassLoader = new ShrinkWrapClassLoader(createArchive());
      
      try
      {
         Class<?> loadedTestClass = archiveClassLoader.loadClass(
               "org.jboss.shrinkwrap.classloader.LoadedTestClass");
         
         Assert.assertNotNull(loadedTestClass);
      } 
      finally
      {
         archiveClassLoader.close();
      }
   }

   @Test
   public void shouldBeAbleToLoadResourceFromArchive() throws Exception
   {
      ShrinkWrapClassLoader archiveClassLoader = new ShrinkWrapClassLoader(createArchive());
      
      try
      {
         URL resource = archiveClassLoader.getResource(
               "org/jboss/shrinkwrap/classloader/LoadedTestClass.class");

         Assert.assertNotNull(resource);
      } 
      finally
      {
         archiveClassLoader.close();
      }
   }
}