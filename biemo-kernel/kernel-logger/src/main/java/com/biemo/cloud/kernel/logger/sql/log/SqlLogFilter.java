/**
 * Copyright  2018-2020 &   (admin@makesoft.cn)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.biemo.cloud.kernel.logger.sql.log;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.Log;

@Slf4j
public class SqlLogFilter implements Log {

    public SqlLogFilter(String clazz) {

    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public boolean isTraceEnabled() {
        return true;
    }

    @Override
    public void error(String s, Throwable e) {
        log.error(s, e);
        SqlHolder.addSqlInfo(s);
    }

    @Override
    public void error(String s) {
        log.error(s);
        SqlHolder.addSqlInfo(s);
    }

    @Override
    public void debug(String s) {
        if (log.isDebugEnabled()) {
            log.debug(s);
        }
        SqlHolder.addSqlInfo(s);
    }

    @Override
    public void trace(String s) {
        if (log.isDebugEnabled()) {
            log.debug(s);
        }
        SqlHolder.addSqlInfo(s);
    }

    @Override
    public void warn(String s) {
        if (log.isDebugEnabled()) {
            log.warn(s);
        }
        SqlHolder.addSqlInfo(s);
    }
}
